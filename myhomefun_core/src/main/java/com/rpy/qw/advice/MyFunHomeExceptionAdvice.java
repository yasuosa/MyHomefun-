package com.rpy.qw.advice;


import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 定义统一异常处理
 * @Auther 任鹏宇
 * @Date 2020/4/23
 */
@RestControllerAdvice
@Slf4j
public class MyFunHomeExceptionAdvice {

    /**
     * 统一处理MyFunHomeException
     *
     * @param e
     */
    @ExceptionHandler(MyFunHomeException.class)
    public Result<Object> exceptionHandler(MyFunHomeException e) {
        log.error("统一异常处理", e);
        return new Result<>(e.getErrorCode(), e.getMessage());
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Object> exceptionHandlerIllegalArgumentException(IllegalArgumentException e) {
        log.error("统一异常处理", e);
        return new Result<Object>(ResultEnum.ERROR.getCode(), "网页错误！请联系管理员");
    }

    @ExceptionHandler(AuthorizationException.class)
    public Result<Object> exceptionHandlerAuthorizationException(AuthorizationException e) {
        log.error("统一异常处理", e);
        return new Result<Object>(ResultEnum.ERROR.getCode(), "无权限！");
    }



    @ExceptionHandler(NullPointerException.class)
    public Result<Object> exceptionHandlerNullPointerException(NullPointerException e) {
        log.error("统一异常处理", e.getMessage());
        e.printStackTrace();
        return new Result<Object>(ResultEnum.ERROR.getCode(), "网页错误！请联系管理员");
    }
}
