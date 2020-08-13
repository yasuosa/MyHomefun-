package com.rpy.qw.controller.post;

import com.rpy.qw.post.domain.Banner;
import com.rpy.qw.post.mapper.BannerMapper;
import com.rpy.qw.post.service.BannerService;
import com.rpy.qw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: myhomefun
 * @description: 轮播图
 * @author: 任鹏宇
 * @create: 2020-08-04 01:11
 **/
@RestController
@RequestMapping("banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;


    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public Result<List<Banner>> getList(){
        List<Banner> data=bannerService.getList();
        return new Result<>(data);
    }
}
