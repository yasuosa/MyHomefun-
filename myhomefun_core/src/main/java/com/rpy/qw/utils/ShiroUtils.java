package com.rpy.qw.utils;

import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.sys.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @program: myfunhome
 * @description: shiro的工具类
 * @author: 任鹏宇
 * @create: 2020-06-21 19:42
 **/
public class ShiroUtils {




    /**
     * 获取activiUser
     * @return
     */
    public static  ActiviUser getActiviUser(){
        Subject subject = SecurityUtils.getSubject();

        if(null == subject || !subject.isAuthenticated()){
            throw new MyFunHomeException(ResultEnum.NOT_LOGIN.getCode(), ResultEnum.NOT_LOGIN.getMsg());
        }
       ActiviUser activiUser  = (ActiviUser) subject.getPrincipal();
        return activiUser;
    }

    /**
     * 获取user
     * @return
     */
    public static User getUser(){
        ActiviUser activiUser = getActiviUser();
        return  activiUser.getUser();
    }


    /**
     * 获得id
     * @return
     */
    public static Integer getId() {
        return getUser().getUserId();
    }

    /**
     * 获得username
     * @return
     */
    public static String getName()  {
        return getUser().getUsername();
    }
}
