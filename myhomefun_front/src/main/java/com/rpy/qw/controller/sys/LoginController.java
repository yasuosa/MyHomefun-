package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.data.SysStaticData;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.service.UserService;
import com.rpy.qw.utils.ActiviUser;
import com.rpy.qw.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: myfunhome
 * @description: 登录
 * @author: 任鹏宇
 * @create: 2020-07-09 14:42
 **/
@RestController
@RequestMapping("login")
@Api(tags = "登录管理")
@ApiSort(1)
public class LoginController {


    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;





    /**
     * 前台登录
     * @param user
     * @return
     */
    @ApiOperation(value = "账号密码登录")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"user.userId","user.address","user.createdTime",
                    "user.headerUrl","user.nickname","user.phone","user.realname",
                    "user.roleIds","user.roleNames","user.salt","user.sex",
                    "user.updateTime","user.userEmail","user.version","user.deleted","user.sign"}
    )
    @RequestMapping(value = "/loginByUserName",method = RequestMethod.POST)
    public Result<Object> loginByUserName(@RequestBody User user){
        Map<String,Object> map=new HashMap<>();
        map=userService.login(user);
        return new Result<>(map, ResultEnum.LOGIN_SUCCESS.getMsg());
    }

    /**
     * 获得登录用户
     * @return
     */
    @ApiOperation(value = "获得登录用户信息")
    @ApiOperationSupport(order=3)
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public Result<ActiviUser> info(){
        ActiviUser activiUser = ShiroUtils.getActiviUser();
        activiUser.getUser().setPassword(null);
        activiUser.getUser().setVersion(null);
        activiUser.getUser().setDeleted(null);
        activiUser.getUser().setSalt(null);
        return new Result<>(activiUser);
    }


    /**
     * 获得登录用户
     * @return
     */
    @ApiOperation(value = "退出登录")
    @ApiOperationSupport(order=4)
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public Result<Object> logout(HttpServletRequest request){
        // shiro退出
        redisTemplate.delete(SysStaticData.USER_REDIS_KEY_PRE+request.getHeader("token"));
        return new Result<>("已退出登录");
    }


}
