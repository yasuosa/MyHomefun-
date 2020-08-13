package com.rpy.qw.controller.biz;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.biz.domain.Goods;
import com.rpy.qw.biz.service.GoodsService;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: myfunhome
 * @description: 商品管理
 * @author: 任鹏宇
 * @create: 2020-07-16 22:30
 **/
@RestController
@RequestMapping("goods")
@Api(tags = "商品管理")
@ApiSort(10)
@RequiresRoles(value = {"超级管理员","业务管理员"},logical = Logical.OR)
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    @ApiOperation(value = "分页查询所有商品",notes = "条件可查询:[id,userContact,type,name]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"goods:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Goods>> getByPage(@RequestBody PageVo<Goods> pageVo){
        PageVo<Goods> data=goodsService.getByPage(pageVo);
        return new Result<>(data);
    }


    @ApiOperation(value = "添加商品")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"goods.id","goods.postId","goods.deleted","goods.createdTime"}
    )
    @RequiresPermissions(value = {"goods:add"})
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Goods goods){
        goodsService.save(goods);
        return new Result<>("保存成功");
    }

    @ApiOperation(value = "删除商品")
    @ApiOperationSupport(order=4
    )
    @RequiresPermissions(value = {"goods:delete"})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        goodsService.removeById(id);
        return new Result<>("删除成功");
    }

    @ApiOperation(value = "更新商品")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"goods.postId","goods.deleted","goods.createdTime"}
    )
    @RequiresPermissions(value = {"goods:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Goods goods){
        goodsService.updateById(goods);
        return new Result<>("更新成功");
    }

}
