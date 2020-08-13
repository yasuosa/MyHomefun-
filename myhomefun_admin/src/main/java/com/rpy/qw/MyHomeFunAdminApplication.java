package com.rpy.qw;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @program: myhomefun
 * @description: 后台启动类
 * @author: 任鹏宇
 * @create: 2020-08-02 03:46
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = {"com.rpy.qw.sys.mapper", "com.rpy.qw.biz.mapper", "com.rpy.qw.post.mapper"})
@EnableCaching
public class MyHomeFunAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyHomeFunAdminApplication.class, args);
    }
}
