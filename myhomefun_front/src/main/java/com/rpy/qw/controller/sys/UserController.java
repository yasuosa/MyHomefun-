package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.ems.SendEmailService;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.service.UserService;
import com.rpy.qw.sys.vo.RegisterUserVo;
import com.rpy.qw.sys.vo.UserHomeVo;
import com.rpy.qw.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * @program: myfunhome
 * @description: 用户管理
 * @author: 任鹏宇
 * @create: 2020-06-21 17:20
 **/

@RestController
@RequestMapping("user")
@Api(tags = "用户管理")
@ApiSort(3)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendEmailService sendEmailService;


    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping(value = "modifyMyData",method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"user.createdTime",
                    "user.roleIds","user.roleNames","user.salt","user.username",
                    "user.updateTime","user.version","user.deleted"}
    )
    public Result<Object> update(@RequestBody User user){
        user.setUserId(ShiroUtils.getId());
        user.setUsername(ShiroUtils.getName());
        userService.updateUser(user);
        return new Result<>("更新成功");
    }



    /**
     * 获取用户首页信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户首页信息")
    @ApiOperationSupport(order=2
    )
    @RequestMapping(value = "/getUserHome/{userId}",method = RequestMethod.GET)
    public Result<UserHomeVo> getUserHome(@PathVariable Integer userId){
        UserHomeVo userAndRoleVo=userService.getUserHome(userId);
        return new Result<>(userAndRoleVo);
    }


    /**
     * 前端修改密码
     * @param user
     * @return
     */
    @RequestMapping(value = "modifyPassWord",method = RequestMethod.PUT)
    @ApiOperation(value = "前端修改密码")
    @ApiOperationSupport(order=3
    )
    public Result<Object> modifyPassWord(@RequestBody User user){
        userService.modifyPassWord(user);
        return new Result<>("修改成功");
    }

    /**
     * 注册
     * @param userVo
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ApiOperation(value = "注册")
    @ApiOperationSupport(order=3
    )
    public Result<Object> register(@RequestBody RegisterUserVo userVo){
        userService.register(userVo);
        return new Result<>("注册成功");
    }


    /**
     * 获取验证码（发送邮箱)
     * @return
     */
    @RequestMapping(value = "/getCode/{userEmail}",method = RequestMethod.GET)
    @ApiOperation(value = "获取验证码（发送邮箱)")
    @ApiOperationSupport(order=3
    )
    public Result<Object> getEmailCode(@PathVariable String userEmail) throws MessagingException {
        sendEmailService.sendCode(userEmail);
        return new Result<>("发送成功！请及时接收");
    }


}
