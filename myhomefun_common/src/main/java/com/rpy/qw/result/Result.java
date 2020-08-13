package com.rpy.qw.result;

import com.rpy.qw.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: myfunhome
 * @description: 返回数据类型
 * @author: 任鹏宇
 * @create: 2020-06-18 22:55
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T>  implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data=null;
    }

    /**
     * 返回成功的类型
     * @param data
     */
    public Result(T data, String msg) {
        this.code= ResultEnum.SUCCESS.getCode();
        this.data = data;
        this.msg=msg;
    }

    public Result(String msg) {
        this.code= ResultEnum.SUCCESS.getCode();
        this.msg = msg;
    }

    public Result(T data) {
        this.code= ResultEnum.SUCCESS.getCode();
        this.msg= ResultEnum.SUCCESS.getMsg();
        this.data = data;
    }
}
