package com.rpy.qw.sys.service;

import com.rpy.qw.sys.domain.Log;
import com.rpy.qw.utils.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.usermodel.Workbook;

public interface LogService extends IService<Log>{


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<Log> getByPage(PageVo<Log> pageVo);


    /**
     * 导出表格
     * @return
     */
    Workbook export();
    
}
