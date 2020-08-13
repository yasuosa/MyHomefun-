package com.rpy.qw.post.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: myfunhome
 * @description: 简单的帖子 （标题 时间 阅读数 留言数)
 * @author: 任鹏宇
 * @create: 2020-07-31 14:10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePostVo implements Serializable {

    private String postId;

    private Integer userId;

    private String username;

    private String postTitle;

    private Date createdTime;

    private Integer readNum;

    private Integer commentNum;
}
