package com.rpy.qw.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.jwt.JWTUtils;
import com.rpy.qw.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: myfunhome
 * @description: 自定义jwt过滤器
 * @author: 任鹏宇
 * @create: 2020-06-18 23:00
 **/
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {


    private static final String TOKEN="token";



    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();
        // 取消了 shiro的session会话管理
        return null != subject && subject.isAuthenticated();
    }




    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }




    /**
     * 认证失败后
     * true 继续
     * flash 结束
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest rq= (HttpServletRequest) request;
//        log.info(rq.getRequestURI());
        String token = rq.getHeader(TOKEN);
        if(null == token || JWTUtils.isExpire(token)){
            log.info("Jwt拦截:url->"+rq.getServletPath());
            log.error("Jwt拦截--token不存在或失效");
            responseTokenError(response);
            return false;
        }

        log.info("Jwt拦截token:"+token);
        JwtToken jwtToken=new JwtToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);
            log.info("Jwt拦截 登录咯++");
        } catch (AuthenticationException e) {
            responseTokenError(response);
            return false;
        }
        return true;
    }

    /**
     * 跳转到登录的时候 拦截
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        Result result=new Result<>(ResultEnum.NOT_LOGIN.getCode(),ResultEnum.NOT_LOGIN.getMsg());;
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }


    /**
     * 对返回值统一处理
     * @param response
     * @throws IOException
     */
    private void responseTokenError(ServletResponse response) throws IOException {
        HttpServletResponse resp= (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new Result<Object>(ResultEnum.NOT_LOGIN.getCode(),ResultEnum.NOT_LOGIN.getMsg())));
    }
}
