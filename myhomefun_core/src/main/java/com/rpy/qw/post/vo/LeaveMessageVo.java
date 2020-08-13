package com.rpy.qw.post.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: myhomefun
 * @description:
 * @author: 任鹏宇
 * @create: 2020-08-02 23:14
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveMessageVo {


    private Integer id;
    private String username;
    private Integer userId;

    @JsonProperty(value = "author")
    private String nickname;


    @JsonProperty(value= "avatar")
    private String header;

    @JsonProperty(value = "body")
    private String content;

    private Date createdTime;


}
