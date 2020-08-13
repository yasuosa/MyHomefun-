package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.post.domain.Comment;
import com.rpy.qw.post.service.CommentService;
import com.rpy.qw.post.vo.CommentVo;
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
 * @description: 评论管理
 * @author: 任鹏宇
 * @create: 2020-07-24 15:53
 **/
@RestController
@RequestMapping("comment")
@Api(tags = "评论管理")
@ApiSort(18)
@RequiresRoles(value = {"超级管理员","帖子管理员"},logical = Logical.OR)
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 前台分页
     */
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    @ApiOperation(value = "分页",notes = "可条件查询[postId,userId]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"comment:select"})
    public Result<PageVo<Comment>> getByPage(@RequestBody PageVo<Comment> pageVo){
        PageVo<Comment> data=commentService.getByPage(pageVo);
        return new Result<>(data);
    }



    /**
     * 删除
     */
    @RequiresPermissions(value = {"comment:delete"})
    @RequestMapping(value = "/delete/{commentId}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除评论")
    public Result<Object> delete(@PathVariable Integer commentId){
        commentService.removeById(commentId);
        return new Result<>("删除成功");
    }

}
