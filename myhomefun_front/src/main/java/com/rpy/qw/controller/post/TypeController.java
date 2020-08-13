package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.post.domain.Type;
import com.rpy.qw.post.service.TypeService;
import com.rpy.qw.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: myfunhome
 * @description: 分类
 * @author: 任鹏宇
 * @create: 2020-07-12 19:13
 **/
@RestController
@RequestMapping("type")
@Api(tags = "分类管理")
@ApiSort(17)
public class TypeController {

    @Autowired
    private TypeService typeService;




    /**
     * 查询所有可用的分类
     * @return
     */
    @ApiOperation(value = "查询所有可用分类")
    @ApiOperationSupport(order=1
    )
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public Result<List<Type>> getList(){
        List<Type> data =typeService.getList();
        return new Result<>(data);
    }

}
