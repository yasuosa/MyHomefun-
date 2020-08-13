package com.rpy.qw.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.biz.domain.Journal;
import com.rpy.qw.biz.mapper.JournalMapper;
import com.rpy.qw.biz.service.JournalService;
import com.rpy.qw.biz.utils.JournalUtils;
import com.rpy.qw.biz.vo.JournalVo;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.utils.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class JournalServiceImpl extends ServiceImpl<JournalMapper, Journal> implements JournalService {
    
    @Autowired
    private JournalMapper journalMapper;
    
    @Override
    public PageVo<JournalVo> getByPage(PageVo<JournalVo> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        Integer id = (Integer) params.get("id");
        Integer userId = (Integer) params.get("userId");
        Integer type = (Integer) params.get("type");
        Integer paymentType= (Integer) params.get("paymentMoneyType");

        QueryWrapper<Journal> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(null !=type,"type",type)
                .eq(null != id,"id",id)
                .eq(null != userId,"user_id",userId)
                .eq(null != type,"type",type)
                .eq(null != paymentType,"payment_money_type",paymentType)
                .eq("deleted",StateEnum.DELETED_FALSE.getCode());
        IPage<Journal> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());

        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }else {
            queryWrapper.orderByDesc("created_time");
        }
        journalMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        List<Journal> records = page.getRecords();
        List<JournalVo> data=new ArrayList<>();
        records.forEach(e ->{
            JournalVo journalVo=new JournalVo();
            BeanUtils.copyProperties(e,journalVo);
            journalVo.setTypeName(JournalUtils.getJournalTypeName(e.getType()));
            data.add(journalVo);
        });
        pageVo.setData(data);
        return pageVo;
    }
}
