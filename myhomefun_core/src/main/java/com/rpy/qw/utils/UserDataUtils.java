package com.rpy.qw.utils;


import com.rpy.qw.data.SysStaticData;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.jwt.JWTUtils;
import com.rpy.qw.sys.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: myfunhome
 * @description: 对于不被过滤的获取对象调用
 * @author: 任鹏宇
 * @create: 2020-06-21 19:42
 **/

@Component
@Slf4j
public class UserDataUtils {

    @Autowired
    @Lazy
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    private static final  String TOKEN="token";

    /**
     * 获取activiUser
     * @return
     */
    public  ActiviUser getActiviUser(){
        if(!isLogin()){
            throw new MyFunHomeException(ResultEnum.NOT_LOGIN.getCode(),ResultEnum.NOT_LOGIN.getMsg());
        }
        String token = request.getHeader(TOKEN);
        if(null == token || JWTUtils.isExpire(token)){
            throw new MyFunHomeException(ResultEnum.NOT_LOGIN.getCode(),ResultEnum.NOT_LOGIN.getMsg());
        }
        ActiviUser activiUser = (ActiviUser) redisTemplate.opsForValue().get(SysStaticData.USER_REDIS_KEY_PRE + token);
        return activiUser;
    }

    /**
     * 判断用户是否存在
     * @return
     */
    public boolean isLogin(){
        String token = request.getHeader(TOKEN);
        if(null == token){
            return false;
        }
        log.info("userDataUtils:token"+token);
        Boolean isLogin = redisTemplate.hasKey(SysStaticData.USER_REDIS_KEY_PRE+token);
        log.info("userDataUtils:是否登录"+isLogin);
        return isLogin;
    }

    /**
     * 获取user
     * @return
     */
    public User getUser(){
        ActiviUser activiUser = getActiviUser();
        return  activiUser.getUser();
    }


    /**
     * 获得id
     * @return
     */
    public Integer getId() {
        return getUser().getUserId();
    }

    /**
     * 获得username
     * @return
     */
    public String getName()  {
        return getUser().getUsername();
    }
}
