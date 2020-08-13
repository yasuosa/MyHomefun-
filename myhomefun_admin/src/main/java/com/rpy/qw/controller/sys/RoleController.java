package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.Role;
import com.rpy.qw.sys.service.RoleService;
import com.rpy.qw.sys.vo.RoleAndMenu;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: myfunhome
 * @description: 角色管理
 * @author: 任鹏宇
 * @create: 2020-06-21 22:58
 **/

@RestController
@RequestMapping("role")
@Api(tags = "角色管理")
@ApiSort(5)
@RequiresRoles("超级管理员")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询所有角色",notes = "条件可查询:[name,remark]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"role:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Role>> getByPage(@RequestBody  PageVo<Role> pageVo){
        PageVo<Role> page=roleService.getByPage(pageVo);
        return new Result<>(page);
    }

    /**
     * 不分页查询所有可用角色
     * @return
     */
    @ApiOperation(value = "查询所有可用角色")
    @ApiOperationSupport(order=2
    )
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public Result<List<Role>> getList(){
        List<Role> data=roleService.getByList();
        return new Result<>(data);
    }


    /**
     * 添加角色
     * @param role
     * @return
     */
    @ApiOperation(value = "添加角色")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"role.id","role.deleted","role.createdTime",
            "role.updatedTime"}
    )
    @RequiresPermissions(value = {"role:add"})
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Role role){
        roleService.saveRole(role);
        return new Result<>("添加成功");
    }


    /**
     * 修改
     * @param role
     * @return
     */
    @ApiOperation(value = "更新角色")
    @ApiOperationSupport(order=4,
            ignoreParameters = {"role.deleted","role.createdTime",
                    "role.updatedTime"}
    )
    @RequiresPermissions(value = {"role:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Role role){
        roleService.updateByRole(role);
        return new Result<>("更新成功");
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除角色")
    @ApiOperationSupport(order=5
    )
    @RequiresPermissions(value = {"role:delete"})
    @RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        roleService.deleteById(id);
        return new Result<>("删除成功");
    }



    /**
     * 获取所属角色 所有菜单
     * @param id
     * @return
     */
    @ApiOperation(value = "获取所属角色所有菜单",notes = "根据角色id查询所拥有的菜单(权限)")
    @ApiOperationSupport(order=6
    )
    @RequestMapping(value = "/getMenuIdsAndPermissionIds/{id}",method = RequestMethod.GET)
    public Result<Map<String,Object>>  getMenuIdsAndPermissionIds(@PathVariable Integer id){
        Map<String,Object> map=new HashMap<>();
        map.put("roleId",id);
        List<Integer> pIds=roleService.getMenuIdsAndPermissionIdsByRoleId(id);
        map.put("mids",pIds);
        return new Result<>(map);
    }


    /**
     * 分配权限和菜单
     */
    @ApiOperation(value = "分配权限和菜单",notes = "根据角色id查询所拥有的菜单(权限)")
    @ApiOperationSupport(order=7
    )
    @RequiresPermissions(value = {"role:permission"})
    @RequestMapping(value = "dispatchPermissionAndMenu",method = RequestMethod.POST)
    public Result<Object> dispatchPermissionAndMenu(@RequestBody RoleAndMenu roleAndMenu){
        roleService.dispatchPermissionAndMenu(roleAndMenu);
        return new Result<>("分配成功");
    }

}
