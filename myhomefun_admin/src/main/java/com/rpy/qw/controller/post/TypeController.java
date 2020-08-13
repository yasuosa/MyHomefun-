package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.post.domain.Type;
import com.rpy.qw.post.service.TypeService;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: myfunhome
 * @description: 分类
 * @author: 任鹏宇
 * @create: 2020-07-12 19:13
 **/
@RestController
@RequestMapping("type")
@Api(tags = "分类管理")
@ApiSort(17)
@RequiresRoles(value = {"超级管理员","帖子管理员"},logical = Logical.OR)
public class TypeController {

    @Autowired
    private TypeService typeService;


    /**
     * 分页查询
     * @return
     */
    @ApiOperation(value = "分页查询所有分类",notes = "条件可查询:[typeName]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"type:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Type>> getByPage(@RequestBody PageVo<Type> pageVo){
        PageVo<Type> page=typeService.getByPage(pageVo);
        return new Result<>(page);
    }




    /**
     * 添加
     * @param type
     * @return
     */
    @ApiOperation(value = "添加分类")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"type.id","type.type_post_count","type.createdTime"}
    )
    @RequiresPermissions(value = {"type:add"})
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Type type){
        typeService.saveType(type);
        return new Result<>("添加成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除分类",notes = "根据分类id删除分类")
    @ApiOperationSupport(order=3
    )
    @RequiresPermissions(value = {"type:delete"})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        typeService.deleteById(id);
        return new Result<>("删除成功");
    }


    /**
     * 更新
     * @param type
     * @return
     */
    @ApiOperation(value = "修改分类")
    @ApiOperationSupport(order=4,
            ignoreParameters = {"type.type_post_count","type.createdTime"}
    )
    @RequiresPermissions(value = {"type:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Type type){
        typeService.updateType(type);
        return new Result<>("更新成功");
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @ApiOperation(value = "启用分类",notes = "根据分类id启用分类")
    @ApiOperationSupport(order=5
    )
    @RequiresPermissions(value = {"type:enable"})
    @RequestMapping(value = "/enable/{id}",method = RequestMethod.GET)
    public Result<Object> enable(@PathVariable Integer id){
        typeService.enable(id);
        return new Result<>("启用成功");
    }


    /**
     * 禁用
     * @param id
     * @return
     */
    @ApiOperation(value = "禁用分类",notes = "根据分类id禁用分类")
    @ApiOperationSupport(order=6
    )
    @RequiresPermissions(value = {"type:disable"})
    @RequestMapping(value = "/disable/{id}",method = RequestMethod.GET)
    public Result<Object> disable(@PathVariable Integer id){
        typeService.disable(id);
        return new Result<>("禁用成功");
    }


    /**
     * 查询所有可用的分类
     * @return
     */
    @ApiOperation(value = "查询所有可用分类")
    @ApiOperationSupport(order=7
    )
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public Result<List<Type>> getList(){
        List<Type> data =typeService.getList();
        return new Result<>(data);
    }
}
