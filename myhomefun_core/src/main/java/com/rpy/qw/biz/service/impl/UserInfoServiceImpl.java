package com.rpy.qw.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.biz.domain.Journal;
import com.rpy.qw.biz.domain.UserInfo;
import com.rpy.qw.biz.mapper.JournalMapper;
import com.rpy.qw.biz.mapper.UserInfoMapper;
import com.rpy.qw.biz.service.UserInfoService;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.utils.ShiroUtils;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.utils.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class
UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    private final static Double BASE_POINTS=50D;

    @Autowired
    private JournalMapper journalMapper;
    
    
    @Override
    public PageVo<UserInfo> getByPage(PageVo<UserInfo> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        String username = (String) params.get("username");

        QueryWrapper<UserInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotBlank(username),"username",username);
        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        IPage<UserInfo> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());

        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        userInfoMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(UserInfo userInfo) {
        Integer version = userInfoMapper.selectVersionById(userInfo.getId());
        QueryWrapper<UserInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",userInfo.getId());
        queryWrapper.eq("version",version);
        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        userInfo.setVersion(version+1);
        userInfoMapper.update(userInfo,queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAccumulatePoints(String username, Double points) {
        QueryWrapper<UserInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        UserInfo old = userInfoMapper.selectOne(queryWrapper);
        if(null == old){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前用户info不存在");
        }
        Integer version = old.getVersion();
        old.setVersion(version+1);
        old.setAccumulatePoints(old.getAccumulatePoints()+points);
        queryWrapper.eq("version",version);
        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        userInfoMapper.update(old,queryWrapper);

        // 加入签到流水
        Journal journal=new Journal();
        journal.setUserId(ShiroUtils.getId());
        journal.setUsername(ShiroUtils.getName());
        journal.setGoodId(-1);
        journal.setRemark(StateEnum.JOURNAL_TYPE_SHIN.getMsg());
        journal.setType(StateEnum.JOURNAL_TYPE_SHIN.getCode());
        journal.setPayMoney(points);
        journal.setAccountMoney(old.getMoney());
        journal.setAccountPoints(old.getAccumulatePoints());
        journal.setPaymentMoneyType(StateEnum.PAYMENT_TYPE_POINTS.getCode());
        journalMapper.insert(journal);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Double signInAddPoints(String username, Integer total) {
        Double addPoints=BASE_POINTS * (1 + (double)(total/60.00f));
        BigDecimal b=new BigDecimal(addPoints);
        double points = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        addAccumulatePoints(username,points);
        return points;
    }

    @Override
    public UserInfo getMyUserInfo() {
        QueryWrapper<UserInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        queryWrapper.eq("user_id", ShiroUtils.getId());
        queryWrapper.eq("username",ShiroUtils.getName());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        return userInfo;
    }
}
