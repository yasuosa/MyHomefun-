package com.rpy.qw.sys.service;

import com.rpy.qw.sys.domain.Notice;
import com.rpy.qw.utils.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface NoticeService extends IService<Notice> {


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<Notice> getByPage(PageVo<Notice> pageVo);


    /**
     * 逻辑删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 启用
     * @param id
     */
    void enable(Integer id);


    /**
     * 禁用
     * @param id
     */
    void disable(Integer id);

    /**
     * 添加
     * @param notice
     */
    void saveNotice(Notice notice);



    List<Notice> getHomeNotice();

}

