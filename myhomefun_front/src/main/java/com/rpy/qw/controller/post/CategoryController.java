package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.post.domain.Category;
import com.rpy.qw.post.service.CategoryService;
import com.rpy.qw.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: myfunhome
 * @description: 专栏
 * @author: 任鹏宇
 * @create: 2020-07-12 19:13
 **/
@RestController
@RequestMapping("/category")
@Api(tags = "专栏管理")
@ApiSort(14)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 查询所有可用的专栏
     * @return
     */
    @ApiOperation(value = "查询所有可用的专栏")
    @ApiOperationSupport(order=1
    )
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public Result<List<Category>> getList(){
        List<Category> data =categoryService.getList();
        return new Result<>(data);
    }


}
