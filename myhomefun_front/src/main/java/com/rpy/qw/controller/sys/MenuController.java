package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.service.MenuService;
import com.rpy.qw.utils.menu.MenuNode;
import com.rpy.qw.utils.menu.MenuParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: myfunhome
 * @description: 菜单管理
 * @author: 任鹏宇
 * @create: 2020-07-09 13:27
 **/
@RestController
@RequestMapping("menu")
@Api(tags = "菜单管理")
@ApiSort(2)
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 得到菜单
     * @return
     */
    @ApiOperation(value = "获取所有菜单")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"params.typecode","params.parentId"}
    )
    @RequestMapping(value = "getMenuList",method = RequestMethod.POST)
    public Result<List<MenuNode>> getMenuList(@RequestBody MenuParams params){
        List<MenuNode> data=menuService.getMenuList(params);
        return new Result<>(data);
    }


    /**
     * 得到所有 (包括权限)
     * @return
     */
    @ApiOperation(value = "获取所有可用菜单(包括权限)")
    @ApiOperationSupport(order=2)
    @RequestMapping(value = "getAll",method = RequestMethod.GET)
    public Result<List<MenuNode>> getMenuList(){
        List<MenuNode> data=menuService.getAll();
        return new Result<>(data);
    }


    /**
     * 得到顶部菜单
     * @return
     */
    @ApiOperation(value = "得到顶部菜单")
    @ApiOperationSupport(order=3)
    @RequestMapping(value = "getTopMenu",method = RequestMethod.GET)
    public Result<List<MenuNode>> getTopMenu(){
       List<MenuNode> data= menuService.getTopMenuList();
       return new Result<>(data);
    }


    /**
     * 得到侧边菜单
     * @return
     */
    @ApiOperation(value = "获取左侧菜单",notes = "根据顶部菜单id获取左侧菜单")
    @ApiOperationSupport(order=4)
    @RequestMapping(value = "/getMenu/{parentId}",method = RequestMethod.GET)
    public Result<List<MenuNode>> getLeftMenu(@PathVariable  Integer parentId){
        List<MenuNode> data= menuService.getLeftMenu(parentId);
        return new Result<>(data);
    }


    /**
     * 添加菜单
     */
    @ApiOperation(value = "添加菜单")
    @ApiOperationSupport(order=5,
            ignoreParameters = {"menuNode.children","menuNode.createTime","menuNode.id",
    "menuNode.parentName","menuNode.updateTime","menuNode.value"}
    )
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody MenuNode menuNode){
        menuService.saveMenu(menuNode);
        return new Result<>("添加成功");
    }


    /**
     * 删除菜单
     */
    @ApiOperation(value = "删除菜单",notes= "根据菜单id删除菜单")
    @ApiOperationSupport(order=6)

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        menuService.deleteById(id);
        return new Result<>("删除成功");
    }


    /**
     * 更新菜单
     */
    @ApiOperation(value = "更新菜单")
    @ApiOperationSupport(order=7,
            ignoreParameters = {"menuNode.children","menuNode.createTime",
                    "menuNode.parentName","menuNode.updateTime","menuNode.value"}
    )
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody MenuNode menuNode){
        menuService.updateById(menuNode);
        return new Result<>("更新成功");
    }



    /**
     * 获取下一个排序吗
     * @return
     */
    @ApiOperation(value = "得到next排序码")
    @RequestMapping(value = "getNextOrderNum",method = RequestMethod.GET)
    @ApiOperationSupport(order=8)
    public Result<Integer> getNextOrderNum(){
        Integer top=menuService.getNextOrderNum();
        return new Result<>(top);
    }
}
