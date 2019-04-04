package com.example.demo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        //设置过滤
        Map<String, String> chainMap = new LinkedHashMap<>(0);
        chainMap.put("/static/**", "anon");
        chainMap.put("/logout", "anon");
        chainMap.put("/login", "anon");
//        chainMap.put("/registerUser", "authc");
        chainMap.put("/registerUser", "perms[admin]");
        //这个限制需要在最后put
//        chainMap.put("/*", "authc");
        //设置登录
        factoryBean.setLoginUrl("/login");
        //登录成功跳转
        factoryBean.setSuccessUrl("/success");
        //登录失败跳转
        factoryBean.setUnauthorizedUrl("/noAuth");
        //设置过滤条件
        factoryBean.setFilterChainDefinitionMap(chainMap);
        return factoryBean;
    }

    @Bean
    public SecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
