package com.rpy.qw.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.biz.domain.Goods;
import com.rpy.qw.biz.domain.Journal;
import com.rpy.qw.biz.domain.Order;
import com.rpy.qw.biz.domain.UserInfo;
import com.rpy.qw.biz.mapper.GoodsMapper;
import com.rpy.qw.biz.mapper.JournalMapper;
import com.rpy.qw.biz.mapper.OrderMapper;
import com.rpy.qw.biz.mapper.UserInfoMapper;
import com.rpy.qw.biz.service.GoodsService;
import com.rpy.qw.data.PostStaticData;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.utils.ShiroUtils;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.utils.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private JournalMapper journalMapper;


    @Override
    public PageVo<Goods> getByPage(PageVo<Goods> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        Integer id = (Integer) params.get("id");
        String userContact = (String) params.get("userContact");
        Integer type = (Integer) params.get("type");
        String name = (String) params.get("name");
        String username = (String) params.get("username");

        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(null !=type,"type",type)
                .eq(null != id,"id",id)
                .like(StringUtils.isNotBlank(userContact),"user_contact",userContact)
                .like(StringUtils.isNotBlank(username),"username",username)
                .like(StringUtils.isNotBlank(name),"name",name);
        IPage<Goods> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());

        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        goodsMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payPoints(Integer goodsId) {
        // 查询商品
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",goodsId);
        queryWrapper.eq("deleted",0);
        Goods goods = goodsMapper.selectOne(queryWrapper);
        if(null == goods){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"商品不存在");
        }
        Integer type = goods.getType();
        Double goodsPrice = goods.getPrice();
        // 积分类型
        if(type.equals(PostStaticData.GOOD_COST_TYPE_MONEY)) {
            // 查询自己积分余额
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"商品类型有误！请联系管理员");
        }
        UserInfo myUserInfo = userInfoMapper.selectUserInfoByUserName(ShiroUtils.getName());
        Double myPoints = myUserInfo.getAccumulatePoints();
        if(myPoints < goodsPrice ){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前积分不足");
        }
        // 减 自己的积分
        QueryWrapper<UserInfo> myW=new QueryWrapper<>();
        myW.eq("version",myUserInfo.getVersion());
        myW.eq("id",myUserInfo.getId());
        myW.eq("user_id",myUserInfo.getUserId());
        myW.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        myUserInfo.setVersion(myUserInfo.getVersion()+1);
        myUserInfo.setAccumulatePoints(myUserInfo.getAccumulatePoints()-goodsPrice);
        userInfoMapper.update(myUserInfo,myW);
        // 买家加入流水
        Journal buyJournal=new Journal();
        buyJournal.setUserId(myUserInfo.getUserId());
        buyJournal.setUsername(myUserInfo.getUsername());
        buyJournal.setType(StateEnum.JOURNAL_TYPE_BUY.getCode());
        buyJournal.setPayMoney(-goodsPrice);
        buyJournal.setAccountMoney(myUserInfo.getMoney());
        buyJournal.setPaymentMoneyType(StateEnum.PAYMENT_TYPE_POINTS.getCode());
        buyJournal.setGoodId(goodsId);
        buyJournal.setAccountPoints(myUserInfo.getAccumulatePoints());
        buyJournal.setRemark(StateEnum.JOURNAL_TYPE_BUY.getMsg());
        journalMapper.insert(buyJournal);


        // 加 卖家的积分
        String providerUserName = goods.getUsername();
        UserInfo providerUserInfo = userInfoMapper.selectUserInfoByUserName(providerUserName);
        QueryWrapper<UserInfo> providerW=new QueryWrapper<>();
        providerW.eq("version",providerUserInfo.getVersion());
        providerW.eq("id",providerUserInfo.getId());
        providerW.eq("user_id",providerUserInfo.getUserId());
        providerW.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        providerUserInfo.setVersion(providerUserInfo.getVersion()+1);
        providerUserInfo.setAccumulatePoints(providerUserInfo.getAccumulatePoints()+goodsPrice);
        userInfoMapper.update(providerUserInfo,providerW);
        // 卖家加入流水
        Journal saleJournal=new Journal();
        saleJournal.setUserId(providerUserInfo.getUserId());
        saleJournal.setUsername(providerUserInfo.getUsername());
        saleJournal.setType(StateEnum.JOURNAL_TYPE_EARN.getCode());
        saleJournal.setPayMoney(goodsPrice);
        saleJournal.setAccountMoney(providerUserInfo.getMoney());
        saleJournal.setPaymentMoneyType(StateEnum.PAYMENT_TYPE_POINTS.getCode());
        saleJournal.setGoodId(goodsId);
        saleJournal.setAccountPoints(providerUserInfo.getAccumulatePoints());
        saleJournal.setRemark(StateEnum.JOURNAL_TYPE_EARN.getMsg());
        journalMapper.insert(saleJournal);


        // 加入订单
        Order order=new Order();
        order.setPostId(goods.getPostId());
        order.setGoodsId(goodsId);
        order.setTitle(goods.getName());
        order.setPrice(goodsPrice);
        order.setUserId(ShiroUtils.getId());
        // 默认1个
        order.setNumber(1);
        order.setPayment("积分支付");
        order.setUsername(ShiroUtils.getName());
        order.setAddress(null);
        order.setStatus(1);
        orderMapper.insert(order);



    }


}
