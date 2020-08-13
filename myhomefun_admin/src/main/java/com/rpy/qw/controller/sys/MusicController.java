package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.Music;
import com.rpy.qw.sys.service.MusicService;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: myfunhome
 * @description: 音乐管理
 * @author: 任鹏宇
 * @create: 2020-06-22 14:47
 **/
@RestController
@RequestMapping("music")
@Api(tags = "音乐管理")
@ApiSort(7)
@RequiresRoles("超级管理员")
public class MusicController {

    @Autowired
    private MusicService musicService;

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    @ApiOperation(value = "分页查看音乐",notes = "条件可查询:[name,artist")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"music.data","pageVo.total","pageVo.pageNum"}

    )
    @RequiresPermissions(value = {"music:select"})
    public Result<PageVo<Music>> getByPage(@RequestBody PageVo<Music> pageVo){
        PageVo<Music> page= musicService.getByPage(pageVo);
        return new Result<>(page);
    }


    /**
     * 添加音乐
     * @param music
     * @return
     */
    @ApiOperation(value = "添加音乐")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"music.createdTime","music.id","music.deleted"}
    )
    @RequiresPermissions(value = {"music:add"})
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Music music){
        musicService.save(music);
        return new Result<>("添加成功");
    }

    /**
     * 删除音乐
     * @param id
     * @return
     */
    @ApiOperation(value = "删除音乐",notes = "根据音乐id删除")
    @ApiOperationSupport(order=4
    )
    @RequiresPermissions(value = {"music:delete"})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        musicService.deleteById(id);
        return new Result<>("删除成功");
    }

    /**
     * 修改音乐
     * @param music
     * @return
     */
    @ApiOperation(value = "修改音乐")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"music.createdTime","music.deleted"}
    )
    @RequiresPermissions(value = {"music:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Music music){
        musicService.updateById(music);
        return new Result<>("更新成功");
    }


    /**
     * 启用音乐
     * @param id
     * @return
     */
    @ApiOperation(value = "启用音乐",notes = "根据音乐id启用音乐")
    @ApiOperationSupport(order=5
    )
    @RequiresPermissions(value = {"music:enable"})
    @RequestMapping(value = "/enable/{id}",method = RequestMethod.GET)
    public Result<Object> enable(@PathVariable Integer id){
        musicService.enable(id);
        return new Result<>("启用成功");
    }


    /**
     * 禁用音乐
     * @param id
     * @return
     */
    @ApiOperation(value = "禁用音乐",notes = "根据音乐id禁用音乐")
    @ApiOperationSupport(order=6
    )
    @RequiresPermissions(value = {"music:disable"})
    @RequestMapping(value = "/disable/{id}",method = RequestMethod.GET)
    public Result<Object> disable(@PathVariable Integer id){
        musicService.disable(id);
        return new Result<>("禁用成功");
    }

}
