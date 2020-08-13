package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.post.domain.Tag;
import com.rpy.qw.post.service.TagService;
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
 * @description: 标签
 * @author: 任鹏宇
 * @create: 2020-07-12 19:13
 **/
@RestController
@RequestMapping("tag")
@Api(tags = "标签管理")
@ApiSort(16)
@RequiresRoles(value = {"超级管理员","帖子管理员"},logical = Logical.OR)
public class TagController {

    @Autowired
    private TagService tagService;


    /**
     * 分页查询
     * @return
     */
    @ApiOperation(value = "分页查询所有标签",notes = "条件可查询:[name,cid]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"tag:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Tag>> getByPage(@RequestBody PageVo<Tag> pageVo){
        PageVo<Tag> page=tagService.getByPage(pageVo);
        return new Result<>(page);
    }

    /**
     * 添加
     * @param tag
     * @return
     */
    @ApiOperation(value = "添加标签")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"tag.id","tag.createdTime","tag.cName"}
    )
    @RequiresPermissions(value = {"tag:add"})
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Tag tag){
        tagService.saveTag(tag);
        return new Result<>("添加成功");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除标签",notes = "根据标签id集合删除标签")
    @ApiOperationSupport(order=5
    )
    @RequiresPermissions(value = {"tag:batchDel"})
    @RequestMapping(value = "/batchDel",method = RequestMethod.PUT)
    public Result<Object> delete(@RequestBody List<Integer> ids){
        tagService.batchDel(ids);
        return new Result<>("删除成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除标签",notes = "根据标签id删除标签")
    @ApiOperationSupport(order=4
    )
    @RequiresPermissions(value = {"tag:delete"})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        tagService.deleteById(id);
        return new Result<>("删除成功");
    }




    /**
     * 更新
     * @param tag
     * @return
     */
    @RequiresPermissions(value = {"tag:update"})
    @ApiOperation(value = "更新标签")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"tag.createdTime","tag.cName"}
    )
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Tag tag){
        tagService.updateTag(tag);
        return new Result<>("更新成功");
    }

}
