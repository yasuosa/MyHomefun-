package com.rpy.qw.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.utils.DateUtils;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.sys.domain.Notice;
import com.rpy.qw.sys.mapper.NoticeMapper;
import com.rpy.qw.sys.service.NoticeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class
NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    

    @SneakyThrows
    @Override
    public PageVo<Notice> getByPage(PageVo<Notice> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        IPage<Notice> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(title),"title",title);
        queryWrapper.like(StringUtils.isNotBlank(content),"content",content);
        queryWrapper.eq("deleted", StateEnum.DELETED_FALSE.getCode());

        if(StringUtils.isNotBlank(startTime)) {
            queryWrapper.ge("created_time", DateUtils.getTime(startTime));
        }
        if(StringUtils.isNotBlank(endTime)) {
            queryWrapper.le( "created_time", DateUtils.getTime(endTime));
        }
        // 排序
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        queryWrapper.orderByDesc("created_time");
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        noticeMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;
    }

    @Override
    public void deleteById(Integer id) {
        Notice notice=new Notice();
        notice.setId(id);
        notice.setDeleted(StateEnum.DELETED_TRUE.getCode());
        noticeMapper.updateById(notice);
    }

    @Override
    public void enable(Integer id) {
        Notice notice=new Notice();
        notice.setId(id);
        notice.setEnable(StateEnum.ENABLE.getCode());
        noticeMapper.updateById(notice);
    }

    @Override
    public void disable(Integer id) {
        Notice notice=new Notice();
        notice.setId(id);
        notice.setEnable(StateEnum.DISABLE.getCode());
        noticeMapper.updateById(notice);
    }

    @Override
    public void saveNotice(Notice notice) {
        notice.setCreatedById(ShiroUtils.getId());
        notice.setCreatedByName(ShiroUtils.getName());
        noticeMapper.insert(notice);
    }

    @Override
    public List<Notice> getHomeNotice() {
        List<Notice> data=noticeMapper.getTwoHomeNotice();
        return data;
    }




}

