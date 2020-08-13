package com.rpy.qw.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.biz.domain.Order;
import com.rpy.qw.biz.mapper.OrderMapper;
import com.rpy.qw.biz.service.OrderService;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.post.domain.Banner;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageVo<Order> getByPage(PageVo<Order> pageVo) {
       Integer id= (Integer) pageVo.getParams().get("id");
       String username= (String) pageVo.getParams().get("username");
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(username),"username",username)
                .eq(null != id,"id",id);
        IPage<Order> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        // 排序
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        orderMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;
    }
}
