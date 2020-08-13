package com.rpy.qw.biz.service;

import com.rpy.qw.biz.domain.Journal;
import com.rpy.qw.biz.vo.JournalVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.utils.PageVo;

public interface JournalService extends IService<Journal>{


    /**+
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<JournalVo> getByPage(PageVo<JournalVo> pageVo);
}
