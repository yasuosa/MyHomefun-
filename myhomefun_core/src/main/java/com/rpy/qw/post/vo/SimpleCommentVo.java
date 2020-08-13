package com.rpy.qw.post.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: myfunhome
 * @description:
 * @author: 任鹏宇
 * @create: 2020-07-31 14:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCommentVo implements Serializable {
    private Integer id;

    private Integer pid;

    private String postId;

    private Integer userId;

    private String username;

    private String postTitle;

    private String nickname;

    private String comment;

    private Date createdTime;


}
