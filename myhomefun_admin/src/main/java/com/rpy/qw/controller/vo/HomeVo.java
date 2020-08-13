package com.rpy.qw.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: myhomefun
 * @description:
 * @author: 任鹏宇
 * @create: 2020-08-07 23:18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeVo implements Serializable {

    private String title;
    private Integer count;
    private String icon;
    private String href;
    private String target;
}
