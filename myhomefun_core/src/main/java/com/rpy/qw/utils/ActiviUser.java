package com.rpy.qw.utils;

import com.rpy.qw.sys.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 带有角色 权限的 用户对象
 * @author: 任鹏宇
 * @create: 2020-06-18 23:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiviUser implements Serializable {

    // user对象
    private User user;

    // 角色
    private List<String> roles;


    // 权限
    private List<String> permissions;
}
