package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.Log;
import com.rpy.qw.sys.service.LogService;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 日志管理
 * @author: 任鹏宇
 * @create: 2020-06-22 14:16
 **/
@RestController
@RequestMapping("log")
@Api(tags = "日志管理")
@ApiSort(8)
@RequiresRoles("超级管理员")
public class LogController {
    @Autowired
    private LogService logService;


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询所有日志",notes = "条件可查询:[url,status,method(GET,POST,DELETE,PUT),ip]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"log:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Log>> getByPage(@RequestBody PageVo<Log> pageVo){
        PageVo<Log> page= logService.getByPage(pageVo);
        return new Result<>(page);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除日志",notes = "根据日志id删除日志")
    @ApiOperationSupport(order=2
    )
    @RequiresPermissions(value = {"log:delete"})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        logService.removeById(id);
        return new Result<>("删除成功");
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequiresPermissions(value = {"log:batchDel"})
    @ApiOperation(value = "批量删除日志",notes = "根据日志id集合批量删除日志")
    @ApiOperationSupport(order=3
    )
    @RequestMapping(value = "/batchDel",method = RequestMethod.PUT)
    public Result<Object> delete(@RequestBody List<Integer> ids){
        logService.removeByIds(ids);
        return new Result<>("删除成功");
    }


    /**
     * 全部导出
     *
     * @throws Exception
     */
    @ApiOperation(value = "导出日志")
    @ApiOperationSupport(order=4
    )
    @RequiresPermissions(value = {"log:export"})
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(HttpServletResponse response) throws Exception {
        Workbook workbook = logService.export();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + "日志");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
