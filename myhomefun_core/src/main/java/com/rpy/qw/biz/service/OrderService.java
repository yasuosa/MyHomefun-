package com.rpy.qw.biz.service;

import com.rpy.qw.biz.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.utils.PageVo;

public interface OrderService extends IService<Order>{


    PageVo<Order> getByPage(PageVo<Order> pageVo);
}
