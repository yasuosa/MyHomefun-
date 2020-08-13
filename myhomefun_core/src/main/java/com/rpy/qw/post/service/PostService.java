package com.rpy.qw.post.service;

import com.rpy.qw.biz.domain.Goods;
import com.rpy.qw.post.domain.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.post.vo.PostAuditingVo;
import com.rpy.qw.post.vo.ReadPostVo;
import com.rpy.qw.post.vo.SimplePostVo;
import com.rpy.qw.utils.PageVo;

import java.util.List;
import java.util.Map;

public interface PostService extends IService<Post> {


    /**
     * 帖子发布
     *
     * @param post
     */
    void publish(Post post);

    /**
     * 分页查询
     *
     * @param pageVo
     * @return
     */
    PageVo<Post> getByPage(PageVo<Post> pageVo);


    /**
     * 审批表的各种细节
     * @param postId
     * @return
     */
    PostAuditingVo getAuditingDetailByAuditingId(String postId);

    /**
     * 根基post删除
     * @param postId
     */
    void deleteById(String postId);

    /**
     * 设置置顶
     * @param postId
     */
    void setTop(String postId);

    /**
     * 设置精华
     * @param postId
     */
    void setPerfect(String postId);

    /**
     * 取消精华
     * @param postId
     */
    void cancelPerfect(String postId);

    /**
     * 取消置顶
     * @param postId
     */
    void cancelTop(String postId);

    /**
     * 更新
     * @param post
     */
    void updatePost(Post post);

    /**
     * 前台分页查询
     * @param pageVo
     * @return
     */
    PageVo<Post> getByPageOfHome(PageVo<Post> pageVo);

    /**
     * 专栏列表的分页查询 带有人物信息
     * @param pageVo
     * @return
     */
    PageVo<Post> getListPage(PageVo<Post> pageVo);

    /**
     * 查询热门
     * @param categoryId
     * @return
     */
    List<Map<String, Object>> getHostPostList(String categoryId);


    /**
     * 查看帖子
     * @param postId
     * @return
     */
    ReadPostVo read(String postId);

    /**
     * 点赞（取消)操作
     * @param postId
     * @return
     */
    String goodPost(String postId);

    /**
     * 收藏（取消）
     * @param postId
     * @return
     */
    String collectPost(String postId);


    /**
     * 查询隐藏商品
     * @param postId
     * @return
     */
    Goods getHideGoods(String postId);


    /**
     * 根据当前用户对帖子的操作(点赞或者收藏)分页查询帖子
     * @param pageVo
     * @return
     */
    PageVo<SimplePostVo> getDeedPostDataByPage(PageVo<SimplePostVo> pageVo);

    /**
     * 分页查询逻辑已删除的帖子
     * @param pageVo
     * @return
     */
    PageVo<SimplePostVo> getDeletePost(PageVo<SimplePostVo> pageVo);

    /**
     * 彻底删除
     * @param postId
     */
    void trueDeletePost(String postId);

    /**
     * 还原帖子
     * @param postId
     */
    void recyclePost(String postId);

    /**
     * 根据userid 获取帖子
     * @param userId
     * @return
     */
    List<Post> getUserPost(Integer userId);
}

