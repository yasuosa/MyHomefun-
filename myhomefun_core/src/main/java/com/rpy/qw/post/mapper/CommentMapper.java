package com.rpy.qw.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.post.domain.Comment;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询点赞中间表
     * @param id
     * @param userId
     * @return
     */
    int selectGoodComment(@Param("commentId") Integer id, @Param("userId") Integer userId);

    /**
     * 添加点赞评论
     * @param commentId
     * @param userId
     */
    void insertGoodComment(@Param("commentId")Integer commentId,@Param("userId") Integer userId);

    /**
     * 删除点赞评论
     * @param commentId
     * @param userId
     */
    void deleteGoodComment(@Param("commentId")Integer commentId,@Param("userId") Integer userId);


    /**
     * 根据userid 和 postid查询评论
     * @param postId
     * @param userId
     * @return
     */
    Comment selectCommentByPostIdAndUserId(@Param("postId") String postId,@Param("userId") Integer userId);

    /**
     * 根据帖子id彻底删除评论
     * @param postId
     */
    void deleteTrueCommentByPostId(String postId);
}