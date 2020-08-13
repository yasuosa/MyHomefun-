package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.service.UserService;
import com.rpy.qw.sys.vo.UserAndRoleVo;
import com.rpy.qw.sys.vo.UserHomeVo;
import com.rpy.qw.sys.vo.UserVo;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequiresRoles("超级管理员")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询分页
     * @param pageVo
     * @return
     */
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    @ApiOperation(value = "分页查询所有用户",notes = "条件可查询:[username,realname," +
            "nickname,userEmail,sex]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"user:select"})
    public Result<PageVo<UserVo>> getByPage(@RequestBody PageVo<UserVo> pageVo){
        PageVo<UserVo> page=userService.getByPage(pageVo);
        return new Result<>(page);
    }
    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"user.createdTime","user.userId",
                    "user.roleIds","user.roleNames","user.salt",
                    "user.updateTime","user.version","user.deleted"}
    )
    @RequiresPermissions(value = {"user:add"})
    public Result<Object> save(@RequestBody User user){
        userService.saveUser(user);
        return new Result<>("添加成功");
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户")
    @RequiresPermissions(value = {"user:update"})
    @ApiOperationSupport(order=3,
            ignoreParameters = {"user.createdTime",
                    "user.roleIds","user.roleNames","user.salt","user.username",
                    "user.updateTime","user.version","user.deleted"}
    )
    public Result<Object> update(@RequestBody User user){
        userService.updateUser(user);
        return new Result<>("更新成功");
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户",notes = "根据用户id删除用户")
    @ApiOperationSupport(order=4
    )
    @RequiresPermissions(value = {"user:delete"})
    public Result<Object> deleteById(@PathVariable Integer id){
        userService.deleteById(id);
        return new Result<>("删除成功");
    }


    /**
     * 查询当个用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "查询单用户",notes = "根据用户id查询用户")
    @ApiOperationSupport(order=5
    )
    public Result<Object> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        user.setPassword(null);
        return new Result<>(user);
    }


    /**
     * 用户角色 的分配
     * @param userAndRoleVo
     * @return
     */
    @ApiOperation(value = "用户角色分配")
    @ApiOperationSupport(order=6,
            ignoreParameters = {"userAndRoleVo.createdTime",
            "userAndRoleVo.roleName","userAndRoleVo.roleId","userAndRoleVo.id"}
    )
    @RequiresPermissions(value = {"user:role"})
    @RequestMapping(value = "dispatchRole",method = RequestMethod.POST)
    public Result<Object> dispatchRole(@RequestBody UserAndRoleVo userAndRoleVo){
        userService.dispatchRole(userAndRoleVo);
        return new Result<>("分配成功");
    }


    /**
     * 取消分配
     * @param userId
     * @return
     */
    @ApiOperation(value = "用户取消角色分配",notes = "根据用户id取消分配角色")
    @ApiOperationSupport(order=7
    )
    @RequiresPermissions(value = {"user:role"})
    @RequestMapping(value = "/cancelRole/{userId}",method = RequestMethod.DELETE)
    public Result<Object> cancelRole(@PathVariable Integer userId){
        userService.cancelRole(userId);
        return new Result<>("取消角色成功");
    }


    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户角色",notes = "根据用户id查询角色")
    @ApiOperationSupport(order=8
    )
    @RequestMapping(value = "/getRoleId/{userId}",method = RequestMethod.GET)
    public Result<Object> getRoleId(@PathVariable Integer userId){
        UserAndRoleVo userAndRoleVo=userService.getRoleId(userId);
        return new Result<>(userAndRoleVo);
    }




}
