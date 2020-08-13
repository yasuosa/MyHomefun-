package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.Link;
import com.rpy.qw.sys.service.LinkService;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: myfunhome
 * @description: 友链
 * @author: 任鹏宇
 * @create: 2020-07-12 19:13
 **/
@RestController
@RequestMapping("link")
@Api(tags = "友链管理")
@ApiSort(9)
@RequiresRoles("超级管理员")
public class LinkController {

    @Autowired
    private LinkService linkService;


    /**
     * 分页查询
     * @return
     */

    @ApiOperation(value = "分页查询所有友链",notes = "条件可查询:[linkName,linkUrl]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"link:select"})
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<Link>> getByPage(@RequestBody PageVo<Link> pageVo){
        PageVo<Link> page=linkService.getByPage(pageVo);
        return new Result<>(page);
    }

    /**
     * 添加
     * @param link
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ApiOperation(value = "添加友链")
    @RequiresPermissions(value = {"link:add"})
    @ApiOperationSupport(order=2,
            ignoreParameters = {"link.id","link.deleted","link.createdTime",
            "link.updateTime","link.version"}
    )
    public Result<Object> save(@RequestBody Link link){
        linkService.save(link);
        return new Result<>("添加成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除友链")
    @ApiOperationSupport(order=4)
    @RequiresPermissions(value = {"link:delete"})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        linkService.removeById(id);
        return new Result<>("删除成功");
    }


    /**
     * 更新
     * @param link
     * @return
     */
    @ApiOperation(value = "更新友链")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"link.deleted","link.createdTime",
                    "link.updateTime","link.version"}
    )
    @RequiresPermissions(value = {"link:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Link link){
        linkService.updateLink(link);
        return new Result<>("更新成功");
    }
}
