package com.rpy.qw.controller.post;

import com.rpy.qw.post.domain.Banner;
import com.rpy.qw.post.service.BannerService;
import com.rpy.qw.post.vo.TreeBannerTypeNode;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: myhomefun
 * @description: 轮播图管理
 * @author: 任鹏宇
 * @create: 2020-08-03 19:13
 **/
@RestController
@RequestMapping("banner")
@RequiresRoles(value = {"超级管理员","帖子管理员"},logical = Logical.OR)
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    @RequiresPermissions(value = {"banner:select"})
    public Result<PageVo<Banner>> getByPage(@RequestBody PageVo<Banner> pageVo){
        PageVo<Banner> data=bannerService.getByPage(pageVo);
        return new Result<>(data);
    }


    @RequestMapping(value = "save")
    @RequiresPermissions(value = {"banner:add"})
    public Result<Object> save(@RequestBody Banner banner){
        bannerService.save(banner);
        return new Result<>("设置成功");
    }

    @RequestMapping(value = "/delete/{id}")
    @RequiresPermissions(value = {"banner:delete"})
    public Result<Object> delete(@PathVariable Integer id){
        bannerService.removeById(id);
        return new Result<>("取消删除");
    }


    @RequestMapping(value = "update")
    @RequiresPermissions(value = {"banner:update"})
    public Result<Object> update(@RequestBody Banner banner){
        bannerService.updateById(banner);
        return new Result<>("取消删除");
    }


    @RequestMapping(value = "getBannerType",method = RequestMethod.GET)
    public Result<List<TreeBannerTypeNode>> getBannerType(){
        List<TreeBannerTypeNode> data=bannerService.getBannerType();
        return new Result<>(data);
    }
}
