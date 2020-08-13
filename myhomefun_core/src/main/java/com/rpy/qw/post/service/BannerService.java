package com.rpy.qw.post.service;

import com.rpy.qw.post.domain.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.post.vo.TreeBannerTypeNode;
import com.rpy.qw.utils.PageVo;

import java.util.List;

public interface BannerService extends IService<Banner>{


    PageVo<Banner> getByPage(PageVo<Banner> pageVo);

    List<TreeBannerTypeNode> getBannerType();

    List<Banner> getList();

}
