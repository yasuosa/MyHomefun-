package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.Notice;
import com.rpy.qw.sys.service.NoticeService;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: myfunhome
 * @description: 公告管理
 * @author: 任鹏宇
 * @create: 2020-07-02 22:11
 **/

@RestController
@RequestMapping("notice")
@Api(tags = "公告管理")
@ApiSort(6)
@RequiresRoles("超级管理员")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    @ApiOperation(value = "分页查看公告",notes = "条件可查询:[title,content,startTime(开始时间),endTime(结束时间)")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequiresPermissions(value = {"notice:select"})
    public Result<PageVo<Notice>> getByPage(@RequestBody PageVo<Notice> pageVo){
        PageVo<Notice> page=noticeService.getByPage(pageVo);
        return new Result<>(page);
    }

    /**
     * 添加
     * @param notice
     * @return
     */
    @ApiOperation(value = "添加公告")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"notice.id","notice.deleted","notice.createdTime",
                    "notice.enable"}
    )
    @RequiresPermissions(value = {"notice:publish"})
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Notice notice){
        noticeService.saveNotice(notice);
        return new Result<>("添加成功");
    }



    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value = "查询单个公告",notes = "根据公告id查询公告")
    @ApiOperationSupport(order=5
    )
    @RequiresPermissions(value = {"notice:show"})
    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
    public Result<Notice> getById(@PathVariable Integer id){
        Notice notice = noticeService.getById(id);
        return new Result<>(notice);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除公告",notes = "根据公告id删除公告")
    @ApiOperationSupport(order=4
    )
    @RequiresPermissions(value = {"notice:delete"})
    @RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id){
        noticeService.deleteById(id);
        return new Result<>("删除成功");
    }


    /**
     * 启用
     * @param id
     * @return
     */
    @ApiOperation(value = "启用公告",notes = "根据公告id启用公告")
    @ApiOperationSupport(order=6
    )
    @RequiresPermissions(value = {"notice:enable"})
    @RequestMapping(value = "/enable/{id}",method = RequestMethod.GET)
    public Result<Object> enable(@PathVariable Integer id){
        noticeService.enable(id);
        return new Result<>("启用成功");
    }


    /**
     * 禁用
     * @param id
     * @return
     */
    @ApiOperation(value = "禁用公告",notes = "根据公告id禁用公告")
    @ApiOperationSupport(order=7
    )
    @RequiresPermissions(value = {"notice:disable"})
    @RequestMapping(value = "/disable/{id}",method = RequestMethod.GET)
    public Result<Object> disable(@PathVariable Integer id){
        noticeService.disable(id);
        return new Result<>("禁用成功");
    }


    /**
     * 修改
     * @param notice
     * @return
     */
    @ApiOperation(value = "修改公告")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"notice.deleted","notice.createdTime",
                    "notice.enable"}
    )
    @RequiresPermissions(value = {"notice:update"})
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Notice notice){
        noticeService.updateById(notice);
        return new Result<>("更新成功");
    }
}
