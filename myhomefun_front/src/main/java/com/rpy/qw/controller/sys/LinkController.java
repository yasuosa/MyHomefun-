package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.sys.service.LinkService;
import io.swagger.annotations.Api;
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
public class LinkController {

    @Autowired
    private LinkService linkService;



}
