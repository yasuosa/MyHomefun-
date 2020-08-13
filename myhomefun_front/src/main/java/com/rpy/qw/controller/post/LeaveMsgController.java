package com.rpy.qw.controller.post;

import com.rpy.qw.post.domain.LeaveMessage;
import com.rpy.qw.post.service.LeaveMessageService;
import com.rpy.qw.post.vo.LeaveMessageVo;
import com.rpy.qw.result.Result;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: myhomefun
 * @description: 留言接口
 * @author: 任鹏宇
 * @create: 2020-08-02 23:12
 **/
@RequestMapping("leaveMessage")
@RestController
public class LeaveMsgController {

    @Autowired
    private LeaveMessageService leaveMessageService;



    @RequestMapping(value = "getListPage",method = RequestMethod.POST)
    public Result<PageVo<LeaveMessageVo>> getListPage(@RequestBody PageVo<LeaveMessageVo> pageVo){
        PageVo<LeaveMessageVo> data=leaveMessageService.getListPage(pageVo);
        return new Result<>(data);
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result<Object> save(@RequestBody LeaveMessage leaveMessage){
        leaveMessage.setUserId(ShiroUtils.getId());
        leaveMessage.setUsername(ShiroUtils.getName());
        leaveMessage.setCreatedTime(new Date());
        leaveMessageService.save(leaveMessage);
        return new Result<>("留言成功");
    }



}
