package com.rpy.qw.config;

import com.alibaba.fastjson.JSON;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.sys.domain.Log;
import com.rpy.qw.sys.service.LogService;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.utils.ThreadLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 请求日志打印
 * @Auther 任鹏宇
 * @Date 2020/4/23
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {


    @Lazy
    @Autowired
    private LogService logService;


    /**
     * 两个..代表所有子目录，最后括号里的两个..代表所有参数
     */
    @Pointcut("execution( * com.rpy.qw.controller.*.*.*(..))")
    public void logPointCut() {
    }

    /**
     * 方法执行之前调用
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        // 记录下请求内容
        printRequestLog(joinPoint, request, uri);
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();
        long time = System.currentTimeMillis() - startTime;
        log.info("耗时 : {}", time);

        Log logger = ThreadLocalContext.get().getLogger();
        logger.setLogTime(time);

        logService.save(logger);
        return ob;
    }



    /**
     * 后置通知
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) {
        log.info("返回值"+ JSON.toJSONString(ret));
        Log logger = ThreadLocalContext.get().getLogger();
        logger.setLogResult(JSON.toJSONString(ret));
    }

    /**
     * 异常通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        Log logger = ThreadLocalContext.get().getLogger();
        logger.setLogStatus(StateEnum.REQUEST_ERROR.getCode());
        logger.setLogMessage(StringUtils.getPackageException(e,"com.rpy"));
        logger.setLogTime(0L);
    }

    /**
     * 打印请求日志
     *
     * @param joinPoint
     * @param request
     * @param uri
     */
    private void printRequestLog(JoinPoint joinPoint, HttpServletRequest request, String uri) {
        // 拿到切面方法
        log.info("请求地址 : {}", uri);
        log.info("请求方式 : {}", request.getMethod());
        // 获取真实的ip地址
        String ip = StringUtils.getRemoteIp(request);
        log.info("IP : {}", ip);
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        log.info("方法 : {}.{}", controllerName, joinPoint.getSignature().getName());
        String params = Arrays.toString(joinPoint.getArgs());
        log.info("请求参数：{}", params);

        //获取日志实体
        Log logger = ThreadLocalContext.get().getLogger();
        logger.setLogUrl(uri);
        logger.setLogParams(params);
        logger.setLogStatus(StateEnum.REQUEST_SUCCESS.getCode());
        logger.setLogMethod(request.getMethod());
        logger.setLogIp(ip);

    }

}