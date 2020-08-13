package com.rpy.qw.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.biz.domain.Journal;
import com.rpy.qw.biz.domain.Sign;
import com.rpy.qw.biz.mapper.JournalMapper;
import com.rpy.qw.biz.mapper.SignMapper;
import com.rpy.qw.biz.service.SignService;
import com.rpy.qw.biz.service.UserInfoService;
import com.rpy.qw.biz.vo.SignMsgVo;
import com.rpy.qw.biz.vo.SignVo;
import com.rpy.qw.data.BizStaticData;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import com.rpy.qw.utils.SignUtils;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class
SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {

    @Autowired
    private SignMapper signMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 签到采用二进制 判断签到表
     * int32 32位   0 二进制   00000000000000000000000000000000000
     * 一个月最多有31天  每个对应的位置 代表每一天的签到情况 0代表未签到  1代表签到
     * 流程 ： （上月流转签到情况 这里不举例说明----->看代码)
     *      这里只说签到的流程和判断是否签到的流程
     *      每月情况:mask
     *      第一次签到  假定时间为 7月13
     *          1.判断是否签到   mask & ( 1 << (dayOfMonth-1))
     *             1.1(假设未签到）
     *               从数据库中取出 mask= 0 , dayOfMonth =13 ,dayOfMonth-1 =12
     *               则 1 二进制位为  01     （1 << 12) 4096二进制位   1000000000000
     *               mask         0000000000000
     *                &                              &运算（双1则1 其余为0 ）
     *               (1 << 12)    1000000000000
     *                0           0000000000000
     *                所以  mask & ( 1 << (dayOfMonth-1)) =0
     *
     *             1.2(假定已签到）
     *               mask(1)        0000000000001
     *                &                              &运算（双1则1 其余为0 ）
     *               (1 << 12)      1000000000000
     *                1             0000000000001
     *           所以当 值不为0是表示当日已签到
     *
     *         2.当日签到    mask | ( 1 << (dayOfMonth-1))
     *
     *              mask(0)         0000000000000
     *                |                             |运算（双0则0 其余为1）
     *             (1 << 12)        1000000000000
     *                              1000000000000    第13位置就被标记了
     *         3.用户没有当日签到
     *           这里不做处理 采用定时器
     *           每天23:59:59 对 连续签到值 做0处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> sigIn() {

        User user = ShiroUtils.getUser();

        // 查询当月是否有签到活动
        QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("date_month", DateUtil.beginOfMonth(new Date()))
                .and(new Consumer<QueryWrapper<Sign>>() {
                    @Override
                    public void accept(QueryWrapper<Sign> signQueryWrapper) {
                        signQueryWrapper.le("date_month", DateUtil.endOfMonth(new Date()));
                    }
                });
        queryWrapper.eq("username", user.getUsername());
        Sign oldNowMonth = signMapper.selectOne(queryWrapper);
        // 如果当月没有签到情况
        if (null == oldNowMonth) {
            oldNowMonth = new Sign();
            oldNowMonth.setUserId(user.getUserId());
            oldNowMonth.setDateMonth(new Date());
            oldNowMonth.setUsername(user.getUsername());
            // 判断是不是当月第一天
            if (SignUtils.isMonthBeginDay(new Date())) {
                // 是首日
                // 查询上个月是否存在连续签到
                QueryWrapper<Sign> q = new QueryWrapper<>();
                q.ge("date_month", DateUtil.beginOfMonth(DateUtil.lastMonth()))
                        .and(new Consumer<QueryWrapper<Sign>>() {
                            @Override
                            public void accept(QueryWrapper<Sign> signQueryWrapper) {
                                signQueryWrapper.le("date_month", DateUtil.endOfMonth(DateUtil.lastMonth()));
                            }
                        }).eq("username", user.getUsername());
                Sign oldLastMonth = signMapper.selectOne(q);
                // 如果上个月存在签到
                if (null != oldLastMonth) {
                    //判断是否是连续签到
                    Integer continueSignMonth = oldLastMonth.getContinueSignMonth();
                    // 是连续签到
                    if (BizStaticData.SIGN_CONTINUE_DAY_NUMBER_ZERO != continueSignMonth) {
                        // 则本月之前的签到连续数 = 上个月连续签到 + 上个月之前
                        oldNowMonth.setBeforeContinueSignMonth(continueSignMonth + oldLastMonth.getBeforeContinueSignMonth());
                    }
                    // 不是
                    else {
                        // 之前无连续签到
                        oldNowMonth.setBeforeContinueSignMonth(BizStaticData.SIGN_CONTINUE_DAY_NUMBER_ZERO);
                    }
                }
                // 上个月不存在
                else {
                    // 之前无连续签到
                    oldNowMonth.setBeforeContinueSignMonth(BizStaticData.SIGN_CONTINUE_DAY_NUMBER_ZERO);
                }
            }
            // 当月没有签到 且不是首日
            else {
                oldNowMonth.setBeforeContinueSignMonth(BizStaticData.SIGN_CONTINUE_DAY_NUMBER_ZERO);
            }
            // 开始签到
            // 设置初始化 签到情况
            oldNowMonth.setContinueSignMonth(BizStaticData.SIGN_CONTINUE_DAY_NUMBER_FIRST);
            oldNowMonth.setMask(SignUtils.sign(null));
            signMapper.insert(oldNowMonth);
        }
        // 当月存在签到
        else {
            // 判断今天 有没有签到
            Integer mask = oldNowMonth.getMask();
            if(SignUtils.isTodaySign(mask)){
                // 签到了
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"你今天已经签到了");
            }
            else{
                // 判断昨天是否签到
                Integer continueSignMonth = oldNowMonth.getContinueSignMonth();
                // 昨天签到了连续
                if(SignUtils.isYerterDaySign(mask)){
                    oldNowMonth.setContinueSignMonth(continueSignMonth+1);
                }else {
                    oldNowMonth.setContinueSignMonth(BizStaticData.SIGN_CONTINUE_DAY_NUMBER_FIRST);
                    oldNowMonth.setBeforeContinueSignMonth(BizStaticData.SIGN_CONTINUE_DAY_NUMBER_ZERO );
                }
                // 开始签到
                oldNowMonth.setDateMonth(new Date());
                oldNowMonth.setUpdatedTime(null);
                oldNowMonth.setMask(SignUtils.sign(mask));
                signMapper.updateById(oldNowMonth);
            }

        }
        // 签到给积分
        // 连续签到数
        Map<String,Object> data=new HashMap<>();
        int total = oldNowMonth.getContinueSignMonth() + oldNowMonth.getBeforeContinueSignMonth();
        Double aDouble = userInfoService.signInAddPoints(user.getUsername(), total);


        data.put("continueToday",total);
        data.put("todayPoints",aDouble);
        return data;
    }

    @Override
    public PageVo<Sign> getByPage(PageVo<Sign> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        String username= (String) params.get("username");
        String dateMonth = (String) params.get("dateMonth");
        Boolean signToday = (Boolean) params.get("signToday");
        IPage<Sign> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        QueryWrapper<Sign> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),"username",username);
        queryWrapper.like(StringUtils.isNotBlank(dateMonth),"date_month",dateMonth);
        // 排序
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        queryWrapper.orderByDesc("date_month","before_continue_sign_month");
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        signMapper.selectPage(page,queryWrapper);

        List<Sign> records = page.getRecords();
        records.forEach(e ->{
            if(SignUtils.isTodaySign(e.getMask())){
                e.setSignToday(true);

            }else {
                e.setSignToday(false);
            }
            e.setTotal(SignUtils.querySignInDayTotal(e.getMask()));
        });
        if(null !=signToday && signToday) {
            records=records.stream().filter(s -> s.isSignToday()).collect(Collectors.toList());
        }
        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(records);
        return pageVo;
    }

    @Override
    public SignMsgVo getMonthSign(String username, String month) {
        QueryWrapper<Sign> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
//        queryWrapper.eq("date_month",DateUtil)
        queryWrapper.ge("date_month", DateUtil.beginOfMonth(DateUtil.parse(month,"yyyy-MM")))
                .and(new Consumer<QueryWrapper<Sign>>() {
                    @Override
                    public void accept(QueryWrapper<Sign> signQueryWrapper) {
                        signQueryWrapper.le("date_month", DateUtil.endOfMonth(DateUtil.parse(month,"yyyy-MM")));
                    }
                });
//        queryWrapper.orderByDesc("date_month");
        List<Sign> signs = signMapper.selectList(queryWrapper);
        // 拿第一个
        SignMsgVo signMsgVo = new SignMsgVo();
        if(signs.size() !=0) {
            Sign sign = signs.get(0);
            Integer mask = sign.getMask();
            List<Integer> data = SignUtils.querySignInDayOfMonth(mask);
            signMsgVo.setYear(DateUtil.year(sign.getDateMonth()));
            signMsgVo.setMonth(DateUtil.month(sign.getDateMonth()) + 1);
            signMsgVo.setSignInDayOfMonth(data);
            signMsgVo.setTotal(data.size());
        }
        return signMsgVo;
    }

    @Override
    public PageVo<SignVo> getTodayAllSign(PageVo<SignVo> pageVo) {
        QueryWrapper<Journal> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode())
                .ge("created_time",DateUtil.beginOfDay(new Date()))
                .and(new Consumer<QueryWrapper<Journal>>() {
                    @Override
                    public void accept(QueryWrapper<Journal> journalQueryWrapper) {
                        journalQueryWrapper.le("created_time",DateUtil.endOfDay(new Date()));
                    }
                })
                .eq("type",StateEnum.JOURNAL_TYPE_SHIN.getCode())
                .orderByDesc("created_time");
        IPage<Journal> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        journalMapper.selectPage(page,queryWrapper);

        List<Journal> records = page.getRecords();
        List<SignVo> data=new ArrayList<>();
        records.forEach(e ->{
            SignVo signVo=new SignVo();
            signVo.setId(data.size());
            signVo.setUserId(e.getUserId());
            signVo.setUsername(e.getUsername());
            signVo.setPoints(e.getPayMoney());
            signVo.setCreatedTime(e.getCreatedTime());
            signVo.setNickname(userMapper.selectNickNameByUserId(e.getUserId()));
            signVo.setHeader(userMapper.selectHeaderByUserId(e.getUserId()));
            data.add(signVo);
        });

        pageVo.setTotal(page.getTotal());
        pageVo.setData(data);
        pageVo.setPageNum(page.getPages());

        return pageVo;
    }

    @Override
    public PageVo<SignVo> getContinueSignDay(PageVo<SignVo> pageVo) {
        IPage<Sign> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        QueryWrapper<Sign> queryWrapper=new QueryWrapper<>();
        queryWrapper.inSql("date_month","SELECT Max(date_month) from biz_sign GROUP BY user_id");
        signMapper.selectPage(page,queryWrapper);
        List<Sign> records = page.getRecords();
        List<SignVo> data=new ArrayList<>();
        records.forEach(e ->{
            SignVo signVo=new SignVo();
            signVo.setId(data.size());
            signVo.setUserId(e.getUserId());
            signVo.setUsername(e.getUsername());
            signVo.setCreatedTime(e.getUpdatedTime());
            signVo.setNickname(userMapper.selectNickNameByUserId(e.getUserId()));
            signVo.setHeader(userMapper.selectHeaderByUserId(e.getUserId()));
            signVo.setContinueTotal(e.getContinueTotal());
            data.add(signVo);
        });


        pageVo.setTotal(page.getTotal());
        pageVo.setData(data.stream().sorted(new Comparator<SignVo>() {
            @Override
            public int compare(SignVo o1, SignVo o2) {
                return o2.getContinueTotal()-o1.getContinueTotal();
            }
        }).collect(Collectors.toList()));
        pageVo.setPageNum(page.getPages());

        return pageVo;
    }
}

