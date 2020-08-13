package com.rpy.qw.controller.biz;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.biz.service.GoodsService;
import com.rpy.qw.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class GoodsController {

    @Autowired
    private GoodsService goodsService;




    @ApiOperation(value = "购买商品")
    @ApiOperationSupport(order=1
    )
    @RequestMapping(value = "/payPoints/{goodsId}",method = RequestMethod.GET)
    public Result<Object> payPoints(@PathVariable Integer goodsId){
        goodsService.payPoints(goodsId);
        return new Result<>("购买成功");
    }

}
