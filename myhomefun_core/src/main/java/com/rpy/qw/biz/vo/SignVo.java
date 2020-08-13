package com.rpy.qw.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: myfunhome
 * @description: 签到展示
 * @author: 任鹏宇
 * @create: 2020-07-29 22:13
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignVo implements Serializable {

    private Integer id;

    private Integer userId;

    private String username;

    private String nickname;

    private String header;

    private Date createdTime;

    private Double points;

    private Integer continueTotal;

}
