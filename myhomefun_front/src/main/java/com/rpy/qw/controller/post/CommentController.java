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
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 前台分页
     */
    @RequestMapping(value = "getListPage",method = RequestMethod.POST)
    @ApiOperation(value = "前台分页",notes = "可条件查询[postId,userId]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    public Result<PageVo<CommentVo>> getListPage(@RequestBody PageVo<CommentVo> pageVo){
        PageVo<CommentVo> data=commentService.getListPage(pageVo);
        return new Result<>(data);
    }

    @ApiOperation(value = "发布评论")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"comment.id","comment.userId","comment.username","comment.createdTime",
            "comment.updatedTime","comment.deleted","comment.goods"}
    )
    @RequestMapping(value = "publish",method = RequestMethod.POST)
    public Result<Object> publish(@RequestBody Comment comment){
        commentService.publish(comment);
        return new Result<>("评论成功");
    }


    @ApiOperation(value = "点赞(取消点赞)评论")
    @ApiOperationSupport(order=3)
    @RequestMapping(value = "/good/{id}",method = RequestMethod.GET)
    public Result<Object> goods(@PathVariable Integer id){
        String msg= commentService.goodsComment(id);
        return new Result<>(msg);
    }


    /**
     * 查询我自己的评论
     */
    @RequestMapping(value = "getMyComment",method = RequestMethod.POST)
    @ApiOperation(value = "查询我自己的评论")
    @ApiOperationSupport(order=4,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    public Result<PageVo<CommentVo>> getMyComment(@RequestBody PageVo<CommentVo> pageVo){
        PageVo<CommentVo> data=commentService.getMyComment(pageVo);
        return new Result<>(data);
    }



    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{commentId}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除我自己的评论")
    @ApiOperationSupport(order=5,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    public Result<Object> delete(@PathVariable Integer commentId){
        commentService.removeById(commentId);
        return new Result<>("删除成功");
    }

}
