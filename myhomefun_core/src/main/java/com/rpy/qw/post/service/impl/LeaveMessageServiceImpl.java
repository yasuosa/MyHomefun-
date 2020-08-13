package com.rpy.qw.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.qw.post.domain.LeaveMessage;
import com.rpy.qw.post.mapper.LeaveMessageMapper;

import com.rpy.qw.post.service.LeaveMessageService;
import com.rpy.qw.post.vo.LeaveMessageVo;
import com.rpy.qw.sys.mapper.UserMapper;
import com.rpy.qw.utils.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveMessageServiceImpl extends ServiceImpl<LeaveMessageMapper, LeaveMessage> implements  LeaveMessageService {


    @Autowired
    private LeaveMessageMapper leaveMessageMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public PageVo<LeaveMessageVo> getListPage(PageVo<LeaveMessageVo> pageVo) {
        QueryWrapper<LeaveMessage> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("created_time");
        IPage<LeaveMessage> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        leaveMessageMapper.selectPage(page,queryWrapper);
        List<LeaveMessageVo> data=new ArrayList<>();
        page.getRecords().forEach(e ->{
            LeaveMessageVo leaveMessageVo=new LeaveMessageVo();
            BeanUtils.copyProperties(e,leaveMessageVo);
            leaveMessageVo.setNickname(userMapper.selectNickNameByUserId(e.getUserId()));
            leaveMessageVo.setHeader(userMapper.selectHeaderByUserId(e.getUserId()));
            data.add(leaveMessageVo);
        });


        pageVo.setData(data);
        pageVo.setTotal(page.getTotal());
        pageVo.setPageNum(page.getPages());
        return pageVo;
    }
}
