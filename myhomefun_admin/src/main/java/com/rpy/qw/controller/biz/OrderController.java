package com.rpy.qw.controller.biz;

import com.baomidou.mybatisplus.extension.api.R;
import com.rpy.qw.biz.domain.Order;
import com.rpy.qw.biz.service.OrderService;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: myhomefun
 * @description: 订单管理
 * @author: 任鹏宇
 * @create: 2020-08-07 15:24
 **/

@RestController
@RequestMapping("order")
@RequiresRoles(value = {"超级管理员","业务管理员"},logical = Logical.OR)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    @RequiresPermissions(value = {"order:select"})
    public Result<PageVo<Order>> getByPage(@RequestBody PageVo<Order> pageVo){
        PageVo<Order> data=orderService.getByPage(pageVo);
        return new Result<>(data);
    }


    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @RequiresPermissions(value = {"order:delete"})
    public Result<Object> delete(@PathVariable Integer id){
        orderService.removeById(id);
        return new Result<>("删除成功");
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    @RequiresPermissions(value = {"order:add"})
    public Result<Object> save(@RequestBody Order order){
        orderService.save(order);
        return new Result<>("添加成功");
    }


    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @RequiresPermissions(value = {"order:update"})
    public Result<Object> update(@RequestBody Order order){
        orderService.updateById(order);
        return new Result<>("修改成功");
    }
}
