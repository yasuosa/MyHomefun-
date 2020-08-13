package com.rpy.qw.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.rpy.qw.file.upload.service.UploadService;
import com.rpy.qw.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: myfunhome
 * @description: 文件上传
 * @author: 任鹏宇
 * @create: 2020-06-23 15:53
 **/
@RestController
@RequestMapping("upload")
@Api(tags = "上传管理")
@ApiSort(99)
public class UploadController {

    @Autowired
    private UploadService uploadService;


    @ApiOperation(value = "上传图片")
    @RequestMapping(value = "uploadImg",method = RequestMethod.POST)
    public Result<Map<String,Object>> uploadImg(MultipartFile mf){
        String path = uploadService.uploadImage(mf);
        Map<String,Object> map=new HashMap<>(2);
        map.put("path",path);
        return new Result<>(map,"上传成功");
    }

    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "uploadFile",method = RequestMethod.POST)
    public Result<Map<String,Object>> uploadFile(MultipartFile mf){
        String path = uploadService.uploadFile(mf);
        Map<String,Object> map=new HashMap<>(2);
        map.put("path",path);
        return new Result<>(map,"上传成功");
    }
}
