package com.rpy.qw.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.excel.entity.ExportParams;
import com.rpy.qw.utils.excel.handler.ExcelExportHandler;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.sys.domain.Log;
import com.rpy.qw.sys.mapper.LogMapper;
import com.rpy.qw.sys.service.LogService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public PageVo<Log> getByPage(PageVo<Log> pageVo) {
        String logUrl = (String) pageVo.getParams().get("url");
        String status = (String) pageVo.getParams().get("status");
        String method = (String) pageVo.getParams().get("method");
        String ip = (String) pageVo.getParams().get("ip");

        IPage<Log> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        QueryWrapper<Log> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(logUrl),"log_url",logUrl)
                .eq(StringUtils.isNotBlank(status),"log_status",status)
                .like(StringUtils.isNotBlank(method),"log_method",method)
                .like(StringUtils.isNotBlank(ip),"log_ip",ip);
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

        logMapper.selectPage(page, queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;

    }

    @Override
    public Workbook export() {

        List<Log> logs = logMapper.selectList(null);
        Workbook workbook =new ExcelExportHandler().createSheet(new ExportParams("日志", "最新日志"), Log.class, logs);
        return workbook;
    }


}
