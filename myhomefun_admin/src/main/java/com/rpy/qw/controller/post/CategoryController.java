package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.post.domain.Category;
import com.rpy.qw.post.service.CategoryService;
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
 * @description: 专栏
 * @author: 任鹏宇
 * @create: 2020-07-12 19:13
 **/
@RestController
@RequestMapping("category")
@Api(tags = "专栏管理")
@ApiSort(14)
@RequiresRoles(value = {"超级管理员","帖子管理员"},logical = Logical.OR)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 分页查询
     * @return
     */
    @ApiOperation(value = "分页查询所有专栏",notes = "条件可查询:[name]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"category:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Category>> getByPage(@RequestBody PageVo<Category> pageVo){
        PageVo<Category> page=categoryService.getByPage(pageVo);
        return new Result<>(page);
    }


    /**
     * 添加
     * @param category
     * @return
     */
    @ApiOperation(value = "添加专栏")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"category.id","category.createdTime","category.updatedTime"}
    )
    @RequiresPermissions(value = {"category:add"})
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Category category){
        categoryService.saveCategory(category);
        return new Result<>("添加成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除专栏",notes = "根据id删除专栏")
    @ApiOperationSupport(order=5
    )
    @RequiresPermissions(value = {"category:delete"})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return new Result<>("删除成功");
    }


    /**
     * 更新
     * @param category
     * @return
     */
    @ApiOperation(value = "修改专栏")
    @ApiOperationSupport(order=4,
            ignoreParameters = {"category.createdTime","category.updatedTime"}
    )
    @RequiresPermissions(value = {"category:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Category category){
        categoryService.updateCategory(category);
        return new Result<>("更新成功");
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @ApiOperation(value = "启用专栏",notes = "根据id启用专栏")
    @ApiOperationSupport(order=6
    )
    @RequiresPermissions(value = {"category:enable"})
    @RequestMapping(value = "/enable/{id}",method = RequestMethod.GET)
    public Result<Object> enable(@PathVariable Integer id){
        categoryService.enable(id);
        return new Result<>("启用成功");
    }


    /**
     * 禁用
     * @param id
     * @return
     */
    @ApiOperation(value = "禁用专栏",notes = "根据id禁用专栏")
    @ApiOperationSupport(order=7
    )
    @RequiresPermissions(value = {"category:disable"})
    @RequestMapping(value = "/disable/{id}",method = RequestMethod.GET)
    public Result<Object> disable(@PathVariable Integer id){
        categoryService.disable(id);
        return new Result<>("禁用成功");
    }

    /**
     * 查询所有可用的专栏
     * @return
     */
    @ApiOperation(value = "查询所有可用的专栏")
    @ApiOperationSupport(order=8
    )
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public Result<List<Category>> getList(){
        List<Category> data =categoryService.getList();
        return new Result<>(data);
    }
}
