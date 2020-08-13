package com.rpy.qw.exception;

import com.rpy.qw.enums.ResultEnum;

/**
 * @program: myfunhome
 * @description: 程序的异常
 * @author: 任鹏宇
 * @create: 2020-06-18 23:09
 **/
public class MyFunHomeException extends RuntimeException{
    private static final long serialVersionUID = 2450214686001409867L;

    private Integer errorCode = ResultEnum.ERROR.getCode();

    public MyFunHomeException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.errorCode = resultEnum.getCode();
    }

    public MyFunHomeException(ResultEnum resultEnum, Throwable throwable) {
        super(resultEnum.getMsg(), throwable);
        this.errorCode = resultEnum.getCode();
    }

    public MyFunHomeException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public MyFunHomeException(String msg) {
        super(msg);
    }

    public MyFunHomeException(Throwable throwable) {
        super(throwable);
    }

    public MyFunHomeException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
