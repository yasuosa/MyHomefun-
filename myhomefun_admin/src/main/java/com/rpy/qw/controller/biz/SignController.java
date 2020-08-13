package com.rpy.qw.controller.biz;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.biz.domain.Sign;
import com.rpy.qw.biz.service.SignService;
import com.rpy.qw.biz.vo.SignMsgVo;
import com.rpy.qw.biz.vo.SignVo;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: myfunhome
 * @description: 签到管理
 * @author: 任鹏宇
 * @create: 2020-07-13 21:22
 **/
@RestController
@RequestMapping("sign")
@Api(tags = "签到管理")
@ApiSort(11)
@RequiresRoles(value = {"超级管理员","业务管理员"},logical = Logical.OR)
public class SignController {

    @Autowired
    private SignService signService;


    /**
     * 分页查询所有
     * @param pageVo
     * @return
     */

    @ApiOperation(value = "分页查询所有签到",notes = "条件可查询:[username,dateMonth,signToday]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"sign:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Sign>> getByPage(@RequestBody PageVo<Sign> pageVo){
        PageVo<Sign> page=signService.getByPage(pageVo);
        return new Result<>(page);
    }



    /**
     * 查询当月的签到情况
     * @param username
     * @return
     */
    @ApiOperation(value = "查询当月签到情况")
    @ApiOperationSupport(order=2
    )
    @RequiresPermissions(value = {"sign:showMonth"})
    @RequestMapping(value = "/getMonthSign/{username}/{month}",method = RequestMethod.GET)
    public Result<SignMsgVo> getMonthSign(@PathVariable String username, @PathVariable String month){
        SignMsgVo signMsgVo= signService.getMonthSign(username,month);
        return new Result<>(signMsgVo);
    }




}
