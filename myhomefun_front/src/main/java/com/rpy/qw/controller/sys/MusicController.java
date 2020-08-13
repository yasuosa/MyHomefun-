package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.result.Result;
import com.rpy.qw.sys.domain.Music;
import com.rpy.qw.sys.service.MusicService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class MusicController {

    @Autowired
    private MusicService musicService;

    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public Result<List<Music>> getList(){
        return new Result<>(musicService.list());
    }
}
