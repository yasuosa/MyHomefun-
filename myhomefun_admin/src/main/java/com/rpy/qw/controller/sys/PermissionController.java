package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.menu.MenuNode;
import com.rpy.qw.sys.service.MenuService;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: myfunhome
 * @description: 权限
 * @author: 任鹏宇
 * @create: 2020-06-21 22:08
 **/

@RestController
@RequestMapping("permission")
@Api(tags = "权限管理")
@ApiSort(4)
@RequiresRoles("超级管理员")
public class PermissionController {

    @Autowired
    private MenuService menuService;


    /**
     * 得到权限
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询所有权限",notes = "条件可查询:[title,typecode,parentId]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"permission:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<MenuNode>> getByPage(@RequestBody PageVo<MenuNode> pageVo){
         PageVo<MenuNode> page=menuService.getPermissionList(pageVo);
        return new Result<>(page);
    }


    /**
     * 添加
     * @param menuNode
     * @return
     */
    @ApiOperation(value = "添加权限")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"menuNode.children","menuNode.createTime","menuNode.id",
                    "menuNode.parentName","menuNode.updateTime","menuNode.value","menuNode.icon",
            "menuNode.path","menuNode.meta","menuNode.component","menuNode.spread"}
    )
    @RequiresPermissions(value = {"permission:add"})
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody MenuNode menuNode){
        menuService.savePermission(menuNode);
        return new Result<>("添加成功！");
    }

    /**
     * 更新
     * @param menuNode
     * @return
     */
    @ApiOperation(value = "更新权限接口")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"menuNode.children","menuNode.createTime",
                    "menuNode.parentName","menuNode.updateTime","menuNode.value","menuNode.icon",
                    "menuNode.path","menuNode.meta","menuNode.component","menuNode.spread"}
    )
    @RequiresPermissions(value = {"permission:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody MenuNode menuNode){
        menuService.updatePermission(menuNode);
        return new Result<>("更新成功！");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除权限",notes = "根据权限id删除权限")
    @ApiOperationSupport(order=4
    )
    @RequiresPermissions(value = {"permission:delete"})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> update(@PathVariable Integer id){
        menuService.deletePermissionById(id);
        return new Result<>("删除成功！");
    }
}
