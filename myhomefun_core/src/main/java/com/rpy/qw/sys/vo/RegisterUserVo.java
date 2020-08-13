package com.rpy.qw.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: myhomefun
 * @description: 注册模型
 * @author: 任鹏宇
 * @create: 2020-08-03 14:31
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserVo implements Serializable {

    private String nickname;

    private String password;

    // 验证码
    private String code;

    private String userEmail;
}
