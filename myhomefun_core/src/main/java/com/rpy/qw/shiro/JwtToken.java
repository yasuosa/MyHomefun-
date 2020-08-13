package com.rpy.qw.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: myfunhome
 * @description: 自定义认证token
 * @author: 任鹏宇
 * @create: 2020-06-18 23:03
 **/
public class JwtToken implements AuthenticationToken {

    // token
    private String token;


    public JwtToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return  token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
