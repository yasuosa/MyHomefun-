package com.rpy.qw.enums;

import lombok.Getter;

/**
 * @program: myfunhome
 * @description: 返回状态码
 * @author: 任鹏宇
 * @create: 2020-06-18 22:49
 **/

@Getter
public enum ResultEnum {

    /**
     *  返回结果枚举 每个枚举代表一个返回状态
     */
    SUCCESS(20000,"操作成功！"),
    ERROR(40000,"操作失败！"),
    DATA_NOY_FOUND(40001,"查询失败"),
    PARAMS_NULL(400002,"参数不能为空"),
    PARAMS_ERROR(40005,"参数不合法"),

    PASSWORD_ERROE(40003,"账号密码错误|请重新填写"),
    NOT_LOGIN(40010,"当前帐号未登录"),
    NOT_LOGIN_TOKEN(40010,"当前token失效"),
    LOGIN_SUCCESS(20000,"登陆成功")
    ;

    Integer code;
    String msg;

    ResultEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
