package com.rpy.qw.sys.service;

import com.rpy.qw.sys.domain.Link;
import com.rpy.qw.utils.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LinkService extends IService<Link>{


    /**
     * 分页
     * @param pageVo
     * @return
     */
    PageVo<Link> getByPage(PageVo<Link> pageVo);


    /**
     * 更新
     * @param link
     */
    void updateLink(Link link);
}
