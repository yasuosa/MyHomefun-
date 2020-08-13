package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.biz.domain.Goods;
import com.rpy.qw.result.Result;
import com.rpy.qw.post.domain.Post;
import com.rpy.qw.post.service.PostService;
import com.rpy.qw.post.vo.ReadPostVo;
import com.rpy.qw.post.vo.SimplePostVo;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: myfunhome
 * @description: 博客管理
 * @author: 任鹏宇
 * @create: 2020-07-14 13:33
 **/
@RestController
@RequestMapping("post")
@Api(tags = "帖子管理")
@ApiSort(15)
@RequiresRoles(value = {"超级管理员","帖子管理员"},logical = Logical.OR)
public class PostController {
    @Autowired
    private PostService postService;


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询所有帖子",notes = "条件可查询:[postId,categoryId,typeId,postTitle,username]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"post:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Post>> getByPage(@RequestBody PageVo<Post> pageVo){
        PageVo<Post> page=postService.getByPage(pageVo);
        return new Result<>(page);
    }



    /**
     * 编程分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询编程帖子",notes = "条件可查询:[postId,categoryId,typeId,postTitle,username]")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"post:code:select"})
    @RequestMapping(value = "/code/getByPage",method = RequestMethod.POST)
    public Result<PageVo<Post>> getByPageOfCode(@RequestBody PageVo<Post> pageVo){
        pageVo.getParams().put("categoryId",1);
        PageVo<Post> page=postService.getByPage(pageVo);
        return new Result<>(page);
    }


    /**
     * 专升本分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询专升本帖子",notes = "条件可查询:[postId,categoryId,typeId,postTitle,username]")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"post:zsb:select"})
    @RequestMapping(value = "/zsb/getByPage",method = RequestMethod.POST)
    public Result<PageVo<Post>> getByPageOfZsb(@RequestBody PageVo<Post> pageVo){
        pageVo.getParams().put("categoryId",2);
        PageVo<Post> page=postService.getByPage(pageVo);
        return new Result<>(page);
    }



    /**
     * 考研分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询考研帖子",notes = "条件可查询:[postId,categoryId,typeId,postTitle,username,userId,isPublic]")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"post:ky:select"})
    @RequestMapping(value = "/ky/getByPage",method = RequestMethod.POST)
    public Result<PageVo<Post>> getByPageOfKy(@RequestBody PageVo<Post> pageVo){
        pageVo.getParams().put("categoryId",3);
        PageVo<Post> page=postService.getByPage(pageVo);
        return new Result<>(page);
    }


    /**
     * 发布
     * @param post
     * @return
     */
    @ApiOperation(value = "发布帖子")
    @ApiOperationSupport(order=4,
            ignoreParameters = {"post.postId","post.userId","post.username",
            "post.post_goods","post.post_read","post.post_collection","post.post_commentNum",
            "post.version","post.deleted","post.createdTime","post.updatedTime",
            "post.categoryName","post.typeName"}
    )
    @RequiresPermissions(value = {"post:publish"})
    @RequestMapping(value = "publish",method = RequestMethod.POST)
    public Result<Object> publish(@RequestBody Post post){
        postService.publish(post);
        return new Result<>("发布成功!请等待审核");
    }


    /**
     * 更新
     * @param post
     * @return
     */
    @ApiOperation(value = "编辑帖子")
    @ApiOperationSupport(order=5,
            ignoreParameters = {"post.userId","post.username",
                    "post.post_goods","post.post_read","post.post_collection","post.post_commentNum",
                    "post.version","post.deleted","post.createdTime","post.updatedTime",
                    "post.categoryName","post.typeName"}
    )
    @RequiresPermissions(value = {"post:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Post post){
        postService.updatePost(post);
        return new Result<>("更新成功！");
    }


    /**
     * 删除
     * @param postId
     * @return
     */
    @ApiOperation(value = "删除帖子", notes = "根据帖子id删除帖子")
    @ApiOperationSupport(order=6
    )
    @RequiresPermissions(value = {"post:delete"})
    @RequestMapping(value = "/delete/{postId}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable String postId){
        postService.deleteById(postId);
        return new Result<>("删除成功");
    }

    /**
     * 设置置顶
     * @param postId
     * @return
     */
    @ApiOperation(value = "设置置顶", notes = "根据帖子id设置帖子置顶")
    @ApiOperationSupport(order=7
    )
    @RequiresPermissions(value = {"post:setTop"})
    @RequestMapping(value = "/setTop/{postId}",method = RequestMethod.GET)
    public Result<Object> setTop(@PathVariable String postId){
        postService.setTop(postId);
        return new Result<>("设置置顶成功！");
    }

    /**
     * 取消置顶
     * @param postId
     * @return
     */
    @ApiOperation(value = "取消置顶", notes = "根据帖子id取消帖子置顶")
    @ApiOperationSupport(order=8
    )
    @RequiresPermissions(value = {"post:cancelTop"})
    @RequestMapping(value = "/cancelTop/{postId}",method = RequestMethod.GET)
    public Result<Object> cancelTop(@PathVariable String postId){
        postService.cancelTop(postId);
        return new Result<>("取消置顶成功！");
    }

    /**
     * 设置精华
     * @param postId
     * @return
     */
    @ApiOperation(value = "设置精贴", notes = "根据帖子id设置精贴")
    @ApiOperationSupport(order=9
    )
    @RequiresPermissions(value = {"post:setPerfect"})
    @RequestMapping(value = "/setPerfect/{postId}",method = RequestMethod.GET)
    public Result<Object> setPerfect(@PathVariable String postId){
        postService.setPerfect(postId);
        return new Result<>("设置精华成功！");
    }

    /**
     * 取消精华
     * @param postId
     * @return
     */
    @ApiOperation(value = "取消精贴", notes = "根据帖子id取消精贴")
    @ApiOperationSupport(order=10
    )
    @RequiresPermissions(value = {"post:cancelPerfect"})
    @RequestMapping(value = "/cancelPerfect/{postId}",method = RequestMethod.GET)
    public Result<Object> cancelPerfect(@PathVariable String postId){
        postService.cancelPerfect(postId);
        return new Result<>("取消精华成功！");
    }

}
