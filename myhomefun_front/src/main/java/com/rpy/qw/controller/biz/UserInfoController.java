package com.rpy.qw.controller.biz;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.biz.domain.UserInfo;
import com.rpy.qw.biz.service.UserInfoService;
import com.rpy.qw.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;



    /**
     * 得到自己的信息
     * @return
     */
    @ApiOperation(value = "得到自己的信息")
    @ApiOperationSupport(order=1
    )
    @RequestMapping(value = "getMyUserInfo",method = RequestMethod.GET)
    public Result<UserInfo> getMyUserInfo(){
        UserInfo userInfo=userInfoService.getMyUserInfo();
        return new Result<>(userInfo);
    }
}
