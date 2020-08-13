package com.rpy.qw.post.service;

import com.rpy.qw.post.domain.Auditing;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.post.vo.AuditingVo;
import com.rpy.qw.post.vo.FeedBackContentVo;
import com.rpy.qw.post.vo.PostAuditingVo;
import com.rpy.qw.utils.PageVo;

public interface AuditingService extends IService<Auditing> {


    /**
     * 分页查询
     *
     * @param pageVo
     * @return
     */
    PageVo<AuditingVo> getByPage(PageVo<AuditingVo> pageVo);

    /**
     * 允许审核通过
     *
     */
    PostAuditingVo allow(FeedBackContentVo feedBackContentVo);


    /**
     * 拒绝审核通过
     *
     */
    PostAuditingVo deny(FeedBackContentVo feedBackContentVo);

    /**
     * 查看详细
     *
     * @param id
     * @return
     */
    PostAuditingVo showDetail(String id);
}

