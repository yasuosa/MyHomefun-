package com.rpy.qw.controller.post;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.post.service.AuditingService;
import com.rpy.qw.post.vo.AuditingVo;
import com.rpy.qw.post.vo.FeedBackContentVo;
import com.rpy.qw.post.vo.PostAuditingVo;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: myfunhome
 * @description: 审核管理
 * @author: 任鹏宇
 * @create: 2020-07-14 20:15
 **/
@RestController
@RequestMapping("auditing")
@Api(tags = "审批管理")
@ApiSort(13)
@Deprecated
public class AuditingController {

    @Autowired
    private AuditingService auditingService;


    /**
     * 分页查询
     * @param pageVo
     * @return
     */

    @ApiOperation(value = "分页查询总审核单",notes = "条件可查询:[id,postId,categoryId,typeId,status,postTitle,username,operateBy]")
    @ApiOperationSupport(order=1,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "getByPage",method = RequestMethod.POST)
    public Result<PageVo<AuditingVo>> getByPage(@RequestBody PageVo<AuditingVo> pageVo){
        PageVo<AuditingVo> page=auditingService.getByPage(pageVo);
        return new Result<>(page);
    }



    /**
     *  专升本分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询专升本专栏审核单",notes = "条件可查询:[id,postId,categoryId,typeId,status,postTitle,username,operateBy]")
    @ApiOperationSupport(order=2,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "/zsb/getByPage",method = RequestMethod.POST)
    public Result<PageVo<AuditingVo>> getByPageOfZsb(@RequestBody PageVo<AuditingVo> pageVo){
        pageVo.getParams().put("categoryId",2);
        PageVo<AuditingVo> page=auditingService.getByPage(pageVo);
        return new Result<>(page);
    }


    /**
     *  编程分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询编程专栏审核单",notes = "条件可查询:[id,postId,categoryId,typeId,status,postTitle,username,operateBy]")
    @ApiOperationSupport(order=3,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "/code/getByPage",method = RequestMethod.POST)
    public Result<PageVo<AuditingVo>> getByPageOfCode(@RequestBody PageVo<AuditingVo> pageVo){
        pageVo.getParams().put("categoryId",1);
        PageVo<AuditingVo> page=auditingService.getByPage(pageVo);
        return new Result<>(page);
    }


    /**
     *  考研分页查询
     * @param pageVo
     * @return
     */
    @ApiOperation(value = "分页查询考研专栏审核单",notes = "条件可查询:[id,postId,categoryId,typeId,status,postTitle,username,operateBy]")
    @ApiOperationSupport(order=4,
            ignoreParameters = {"pageVo.data","pageVo.total","pageVo.pageNum"}
    )
    @RequestMapping(value = "/ky/getByPage",method = RequestMethod.POST)
    public Result<PageVo<AuditingVo>> getByPageOfKy(@RequestBody PageVo<AuditingVo> pageVo){
        pageVo.getParams().put("categoryId",3);
        PageVo<AuditingVo> page=auditingService.getByPage(pageVo);
        return new Result<>(page);
    }





    /***
     * 审批表的各种细节
     * @param auditingId
     * @return
     */
    @ApiOperation(value = "审批表的各种细节",notes = "根据审核表单id查询更多")
    @ApiOperationSupport(order=5
    )
    @RequestMapping(value = "/showDetail/{auditingId}",method = RequestMethod.GET)
    public Result<PostAuditingVo> showDetail(@PathVariable String auditingId){
        return new Result<>(auditingService.showDetail(auditingId));
    }


    /**
     * 通过
     * @return
     */
    @ApiOperation(value = "审批通过",notes = "根据审核表单id查询更多")
    @ApiOperationSupport(order=6
    )
    @RequestMapping(value = "allow",method = RequestMethod.PUT)
    public Result<Object> allow(@RequestBody FeedBackContentVo feedBackContentVo){
        auditingService.allow(feedBackContentVo);
        return new Result<>("操作成功");
    }


    /**
     * 驳回
     * @return
     */
    @ApiOperation(value = "审批驳回",notes = "根据审核表单id查询更多")
    @ApiOperationSupport(order=7
    )
    @RequestMapping(value = "deny",method = RequestMethod.PUT)
    public Result<Object> deny(@RequestBody FeedBackContentVo feedBackContentVo){
        auditingService.deny(feedBackContentVo);
        return new Result<>("操作成功");
    }

}
