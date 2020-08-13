package com.rpy.qw.post.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: myfunhome
 * @description: 评论展示页面
 * @author: 任鹏宇
 * @create: 2020-07-24 15:54
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "评论展示模型")
public class CommentVo {

    @ApiModelProperty(value = "评论id")
    private Integer id;

    @ApiModelProperty(value = "上级id")
    private Integer pid;

    @ApiModelProperty(value = "帖子id")
    private String postId;

    @ApiModelProperty(value = "帖子标题")
    private String postTitle;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户姓名id")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    @JsonProperty("author")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    @JsonProperty("avatar")
    private String header;

    @ApiModelProperty(value = "点赞数")
    private Integer goods;

    @ApiModelProperty(value = "评论内容")
    @JsonProperty("body")
    private String comment;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;


    @ApiModelProperty(value = "是否点赞了")
    private boolean isGood;
}
