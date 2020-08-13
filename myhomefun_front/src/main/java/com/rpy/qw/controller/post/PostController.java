package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.biz.domain.Goods;
import com.rpy.qw.post.domain.Post;
import com.rpy.qw.post.service.PostService;
import com.rpy.qw.post.vo.ReadPostVo;
import com.rpy.qw.post.vo.SimplePostVo;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @RequestMapping(value = "getMyAllPost",method = RequestMethod.POST)
    public Result<PageVo<Post>> getMyAllPost(@RequestBody PageVo<Post> pageVo){
        pageVo.getParams().put("userId", ShiroUtils.getId());
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
    @RequestMapping(value = "/delete/{postId}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable String postId){
        postService.deleteById(postId);
        return new Result<>("删除成功");
    }





    /**
     * 前台分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "前台首页分页查询",notes = "条件可查询:[postId,categoryId,typeId,postTitle,username]")
    @ApiOperationSupport(order=11,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "getByPageOfHome",method = RequestMethod.POST)
    public Result<PageVo<Post>> getByPageOfHome(@RequestBody PageVo<Post> pageVo){
        PageVo<Post> page=postService.getByPageOfHome(pageVo);
        return new Result<>(page);
    }

    /**
     * 查询用户的所有发布帖子
     * @return
     */
    @ApiOperation(value = "查询用户的所有发布帖子")
    @ApiOperationSupport(order=12,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "getUserPost/{userId}",method = RequestMethod.GET)
    public Result<List<Post>> getUserPost(@PathVariable Integer userId){
        List<Post>data=postService.getUserPost(userId);
        return new Result<>(data);
    }

    /**
     * 前台分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "带有人物信息的查询",notes = "条件可查询:[postId,categoryId,typeId,postTitle,username]")
    @ApiOperationSupport(order=12,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "getListPage",method = RequestMethod.POST)
    public Result<PageVo<Post>> getListPage(@RequestBody PageVo<Post> pageVo){
        PageVo<Post> page=postService.getListPage(pageVo);
        return new Result<>(page);
    }




    /**
     * 前台查询热门帖子

     * @return
     */
    @ApiOperation(value = "前台查询热门帖子")
    @ApiOperationSupport(order=13
    )
    @RequestMapping(value = "/getHostPostList/{categoryId}",method = RequestMethod.GET)
    public Result<List<Map<String,Object>>> getHostPostList(@PathVariable String categoryId){
        List<Map<String,Object>> data=postService.getHostPostList(categoryId);
        return new Result<>(data);
    }

    /**
     * 查看帖子
     * @return
     */
    @ApiOperation(value = "查看帖子")
    @ApiOperationSupport(order=14
    )
    @RequestMapping(value = "/read/{postId}",method = RequestMethod.GET)
    public Result<ReadPostVo> read(@PathVariable String postId){
        ReadPostVo post=postService.read(postId);
        return new Result<>(post);
    }

    /**
     * 点赞操作
     * @return
     */
    @ApiOperation(value = "点赞操作")
    @ApiOperationSupport(order=15
    )
    @RequestMapping(value = "/good/{postId}",method = RequestMethod.GET)
    public Result<Object> goodPost(@PathVariable String postId){
        String msg=postService.goodPost(postId);
        return new Result<>(msg);
    }

    /**
     * 收藏操作
     * @return
     */
    @ApiOperation(value = "收藏操作")
    @ApiOperationSupport(order=16
    )
    @RequestMapping(value = "/collect/{postId}",method = RequestMethod.GET)
    public Result<Object> collectPost(@PathVariable String postId){
        String msg=postService.collectPost(postId);
        return new Result<>(msg);
    }

    /**
     * 收藏操作
     * @return
     */
    @ApiOperation(value = "查看帖子的商品")
    @ApiOperationSupport(order=16
    )
    @RequestMapping(value = "/getHideGoods/{postId}",method = RequestMethod.GET)
    public Result<Goods> getHideGoods(@PathVariable String postId){
        Goods goods=postService.getHideGoods(postId);
        return new Result<>(goods);
    }

    /**
     * 根据当前用户对帖子的操作(点赞或者收藏)分页查询帖子
     * @return
     */
    @ApiOperation(value = "根据当前用户对帖子的操作(点赞或者收藏)分页查询帖子",notes = "必填 条件可查询:[userId,deedType]")
    @ApiOperationSupport(order=17,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "/getDeedPostDataByPage",method = RequestMethod.POST)
    public Result<PageVo<SimplePostVo>> getDeedPostDataByPage(@RequestBody PageVo<SimplePostVo> pageVo){
        pageVo.getParams().put("userId", ShiroUtils.getId());
        PageVo<SimplePostVo> data=postService.getDeedPostDataByPage(pageVo);
        return new Result<>(data);
    }



    /**
     * 获取当前用户的逻辑已删除的帖子
     * @return
     */
    @ApiOperation(value = "获取当前用户的逻辑已删除的帖子")
    @ApiOperationSupport(order=18,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "/getDeletePost",method = RequestMethod.POST)
    public Result<PageVo<SimplePostVo>> getDeletePost(@RequestBody PageVo<SimplePostVo> pageVo){
        PageVo<SimplePostVo> data=postService.getDeletePost(pageVo);
        return new Result<>(data);
    }


    /**
     * 彻底删除帖子
     * @return
     */
    @ApiOperation(value = "彻底删除帖子")
    @ApiOperationSupport(order=19
    )
    @RequestMapping(value = "/trueDeletePost/{postId}",method = RequestMethod.DELETE)
    public Result<Object> trueDeletePost(@PathVariable String postId){
        postService.trueDeletePost(postId);
        return new Result<>("删除成功");
    }

    /**
     * 还原帖子
     * @return
     */
    @ApiOperation(value = "还原帖子")
    @ApiOperationSupport(order=20
    )
    @RequestMapping(value = "/recyclePost/{postId}",method = RequestMethod.GET)
    public Result<Object> recyclePost(@PathVariable String postId){
        postService.recyclePost(postId);
        return new Result<>("还原成功");
    }
}
