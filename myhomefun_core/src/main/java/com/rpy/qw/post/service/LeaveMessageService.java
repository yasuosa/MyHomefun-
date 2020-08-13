package com.rpy.qw.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.post.domain.LeaveMessage;
import com.rpy.qw.post.vo.LeaveMessageVo;
import com.rpy.qw.utils.PageVo;

public interface LeaveMessageService extends IService<LeaveMessage> {

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<LeaveMessageVo> getListPage(PageVo<LeaveMessageVo> pageVo);
}
