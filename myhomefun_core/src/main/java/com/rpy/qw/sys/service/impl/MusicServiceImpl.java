package com.rpy.qw.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.sys.domain.Music;
import com.rpy.qw.sys.mapper.MusicMapper;
import com.rpy.qw.sys.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public void deleteById(Integer id) {
        Music music=new Music();
        music.setId(id);
        music.setDeleted(StateEnum.DELETED_TRUE.getCode());
        musicMapper.updateById(music);
    }

    @Override
    public PageVo<Music> getByPage(PageVo<Music> pageVo) {
        String name = (String) pageVo.getParams().get("name");
        String artist = (String) pageVo.getParams().get("artist");
        IPage<Music> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        QueryWrapper<Music> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name);
        queryWrapper.like(StringUtils.isNotBlank(artist),"artist",artist);
        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode());

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
        musicMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;
    }

    @Override
    public void enable(Integer id) {
        Music music=new Music();
        music.setId(id);
        music.setEnable(StateEnum.ENABLE.getCode());
        musicMapper.updateById(music);
    }

    @Override
    public void disable(Integer id) {
        Music music=new Music();
        music.setId(id);
        music.setEnable(StateEnum.DISABLE.getCode());
        musicMapper.updateById(music);
    }
}
