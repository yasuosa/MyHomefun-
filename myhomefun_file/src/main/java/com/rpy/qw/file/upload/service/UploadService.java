package com.rpy.qw.file.upload.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.file.upload.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Component
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {


    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties prop;


    /**
     * 上传图片
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) {
        // 1、校验文件类型
        String contentType = file.getContentType();
        if (!prop.getAllowTypes().contains(contentType)) {
            throw new RuntimeException("文件类型不支持");
        }
        // 2、校验文件内容
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null || image.getWidth() == 0 || image.getHeight() == 0) {
                throw new RuntimeException("上传文件有问题");
            }
        } catch (IOException e) {
            log.error("校验文件内容失败....{}", e);
            throw new RuntimeException("校验文件内容失败"+e.getMessage());
        }

        try {
            // 3、上传到FastDFS
            // 3.1、获取扩展名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            // 3.2、上传
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            // 返回路径
            return prop.getBaseUrl() + storePath.getFullPath();
        } catch (Exception e) {
            log.error("【文件上传】上传文件失败！....{}", e);
            throw  new MyFunHomeException("【文件上传】上传文件失败！"+e.getMessage());
        }
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) {
        try {
            // 3、上传到FastDFS
            // 3.1、获取扩展名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            // 3.2、上传
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            // 返回路径
            return prop.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            log.error("【文件上传】上传文件失败！....{}", e);
            throw  new MyFunHomeException("【文件上传】上传文件失败！"+e.getMessage());
        }
    }

    
}
