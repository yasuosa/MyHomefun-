package com.rpy.qw.post.service;

import com.rpy.qw.post.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.post.vo.CommentVo;
import com.rpy.qw.utils.PageVo;

public interface CommentService extends IService<Comment>{


    /**
     * 前台分页查询
     * @param pageVo
     * @return
     */
    PageVo<CommentVo> getListPage(PageVo<CommentVo> pageVo);

    /**
     * 发布公告
     * @param comment
     */
    void publish(Comment comment);

    /**
     * 点赞评论操作
     * @param id
     * @return
     */
    String goodsComment(Integer id);

    PageVo<CommentVo> getMyComment(PageVo<CommentVo> pageVo);

    PageVo<Comment> getByPage(PageVo<Comment> pageVo);
}
