package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.Notice;
import com.rpy.qw.sys.service.NoticeService;
import com.rpy.qw.utils.PageVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "getHomeNotice",method = RequestMethod.GET)
    public Result<List<Notice>> getHomeNotice(){
        List<Notice> data= noticeService.getHomeNotice();
        return new Result<>(data);
    }

    @RequestMapping(value = "/read/{noticeId}",method = RequestMethod.GET)
    public Result<Notice> read(@PathVariable Integer noticeId){
        Notice notice = noticeService.getById(noticeId);
        notice.setDeleted(null);
        notice.setEnable(null);
        return new Result<>(notice);
    }

}
