package com.rpy.qw.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.sys.domain.Link;
import com.rpy.qw.sys.mapper.LinkMapper;
import com.rpy.qw.sys.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public PageVo<Link> getByPage(PageVo<Link> pageVo) {
        String linkName = (String) pageVo.getParams().get("linkName");
        String linkUrl = (String) pageVo.getParams().get("linkUrl");
        QueryWrapper<Link> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(linkName),"link_name",linkName);
        queryWrapper.like(StringUtils.isNotBlank(linkUrl),"link_url",linkUrl);
        queryWrapper.eq("deleted", StateEnum.DELETED_FALSE.getCode());
        IPage<Link> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        // 排序
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        linkMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLink(Link link) {
        // 先查
        Link old = linkMapper.selectById(link.getLinkId());
        Integer version = old.getVersion();

        // 在更新
        QueryWrapper<Link> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("version",version);
        queryWrapper.eq("link_id",link.getLinkId());
        link.setVersion(version+1);

        linkMapper.update(link,queryWrapper);
    }
}
