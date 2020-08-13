package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.post.domain.Tag;
import com.rpy.qw.post.service.TagService;
import com.rpy.qw.post.vo.TagVo;
import com.rpy.qw.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: myfunhome
 * @description: 标签
 * @author: 任鹏宇
 * @create: 2020-07-12 19:13
 **/
@RestController
@RequestMapping("tag")
@Api(tags = "标签管理")
@ApiSort(16)
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 根据专栏id获取可用标签
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "根据专栏id获取可用标签")
    @ApiOperationSupport(order=1
    )
    @RequestMapping(value = "/getList/{categoryId}",method = RequestMethod.GET)
    public Result<List<TagVo>> getListByCategroyId(@PathVariable Integer categoryId){
        List<TagVo> data= tagService.getListByCategroyId(categoryId);
        return new Result<>(data);
    }

    /**
     * 获取可用标签
     * @return
     */
    @ApiOperation(value = "获取可用标签")
    @ApiOperationSupport(order=2
    )
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public Result<List<TagVo>> getList(){
        List<TagVo> data= tagService.getList();
        return new Result<>(data);
    }
}
