package com.rpy.qw;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * @program: myhomefun
 * @description: 前台启动类
 * @author: 任鹏宇
 * @create: 2020-08-02 03:46
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.rpy.qw.sys.mapper", "com.rpy.qw.biz.mapper", "com.rpy.qw.post.mapper"})
@EnableCaching
public class MyHomeFunFrontApplication  {
    public static void main(String[] args) {
        SpringApplication.run(MyHomeFunFrontApplication.class, args);
    }
}
