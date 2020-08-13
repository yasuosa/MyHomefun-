package com.rpy.qw.post.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: myfunhome
 * @description: 前台展示隐藏资源
 * @author: 任鹏宇
 * @create: 2020-07-25 21:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostHideContentVo {

    private Integer id;

    /**
     * 内容类型
     */
    private String typeName;

    private String postId;

    private String resource;

    private Double price;


}
