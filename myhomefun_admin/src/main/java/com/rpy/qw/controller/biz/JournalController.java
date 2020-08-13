package com.rpy.qw.controller.biz;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.rpy.qw.biz.service.JournalService;
import com.rpy.qw.biz.vo.JournalVo;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: myfunhome
 * @description: 流水管理
 * @author: 任鹏宇
 * @create: 2020-07-28 19:41
 **/
@RestController
@Api("流水管理")
@RequestMapping("journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @ApiOperation(value = "分页查询所有流水",notes = "条件可查询:[]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping("getByPage")
    public Result<PageVo<JournalVo>> getByPage(@RequestBody PageVo<JournalVo> pageVo){
        PageVo<JournalVo>page =journalService.getByPage(pageVo);
        return new Result<>(page);
    }

}
