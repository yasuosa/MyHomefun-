package com.rpy.qw.config;



import com.rpy.qw.shiro.JwtFilter;
import com.rpy.qw.shiro.realm.AdminUserRealm;
import com.rpy.qw.shiro.realm.FrontUserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */

@Configuration
public class ShiroAutoConfiguration {

    /**
     * 声明userRealm
     */
    @Bean("userRealm")
    public FrontUserRealm userRealm() {
        return new FrontUserRealm();
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(FrontUserRealm  realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(realm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }
//
//    @Bean("jwtFilter")
//    public JwtFilter jwtFilter(){
//        return new JwtFilter();
//    }


    /**
     * 配置shiro的过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filters=new HashMap<>();
        // 添加自动逸 jwtfilter
        JwtFilter jwtFilter=new JwtFilter();
        filters.put("jwt",jwtFilter);
        //配置过滤器
        factoryBean.setFilters(filters);
        // 设置安全管理器
        factoryBean.setSecurityManager(securityManager);

        // 设置未登陆的时要跳转的页面
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 自定义过滤器
        filterChainDefinitionMap.put("/login/admin/login", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");

        // swagger
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/service-worker.js", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");

        // 前台接口
        filterChainDefinitionMap.put("/category/getList","anon");
        filterChainDefinitionMap.put("/post/getByPageOfHome","anon");
        filterChainDefinitionMap.put("/post/getListPage","anon");
        filterChainDefinitionMap.put("/post/getHostPostList/**","anon");
        filterChainDefinitionMap.put("/post/getTagPostPage","anon");
        filterChainDefinitionMap.put("/post/read/**","anon");
        filterChainDefinitionMap.put("/tag/getList/**","anon");
        filterChainDefinitionMap.put("/login/loginByUserName","anon");
        filterChainDefinitionMap.put("/comment/getListPage","anon");
        filterChainDefinitionMap.put("/sign/getTodayAllSign","anon");
        filterChainDefinitionMap.put("/sign/getContinueSignDay","anon");
        filterChainDefinitionMap.put("/user/getUserHome/**","anon");
        filterChainDefinitionMap.put("/post/getUserPost/**","anon");
        filterChainDefinitionMap.put("/leaveMessage/getListPage","anon");
        filterChainDefinitionMap.put("/tag/getList","anon");
        filterChainDefinitionMap.put("/user/register","anon");
        filterChainDefinitionMap.put("/user/getCode/**","anon");
        filterChainDefinitionMap.put("/banner/getList","anon");
        filterChainDefinitionMap.put("/notice/getHomeNotice","anon");
        filterChainDefinitionMap.put("/notice/read/**","anon");
        filterChainDefinitionMap.put("/music/getList","anon");


        filterChainDefinitionMap.put("/type/getList","anon");

        filterChainDefinitionMap.put("/**","jwt");

        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}