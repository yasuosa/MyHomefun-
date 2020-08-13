package com.rpy.qw.sys.service;

import com.rpy.qw.sys.domain.Music;
import com.rpy.qw.utils.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MusicService extends IService<Music>{

    /**
     * 删除音乐
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<Music> getByPage(PageVo<Music> pageVo);

    /**
     * 启动
     * @param id
     */
    void enable(Integer id);

    /**
     * 禁用
     * @param id
     */
    void disable(Integer id);
}
