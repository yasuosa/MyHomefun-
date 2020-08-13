package com.rpy.qw.controller.biz;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.biz.domain.UserInfo;
import com.rpy.qw.biz.service.UserInfoService;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: myfunhome
 * @description: 用户info的管理
 * @author: 任鹏宇
 * @create: 2020-07-18 20:08
 **/

@Api(tags = "用户资金管理")
@ApiSort(12)
@RestController
@RequestMapping("userInfo")
@RequiresRoles(value = {"超级管理员","业务管理员"},logical = Logical.OR)
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询所有数据",notes = "条件可查询:[username]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"userInfo:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<UserInfo>> getByPage(@RequestBody PageVo<UserInfo> pageVo){
        PageVo<UserInfo> page=userInfoService.getByPage(pageVo);
        return new Result<>(page);
    }

    /**
     * 更新
     * @param userInfo
     * @return
     */
    @ApiOperation(value = "更新数据")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"userInfo.createdTime","userInfo.version"}
    )
    @RequiresPermissions(value = {"userInfo:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody UserInfo userInfo){
        userInfoService.updateInfo(userInfo);
        return new Result<>("更新成功");
    }
}
