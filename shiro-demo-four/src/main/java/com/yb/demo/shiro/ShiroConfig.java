package com.yb.demo.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        Map<String, String> chainMap = new LinkedHashMap<>();
        //放开static下的静态资源
        chainMap.put("/static/**", "anon");
        //放开登出
        chainMap.put("/logout", "anon");
        //放开生成验证码的接口
        chainMap.put("/verifyCode", "anon");
        //放开校验验证码的接口
        chainMap.put("/verifyCodeCheck", "anon");
        //放开跳转登录页面的接口
        chainMap.put("/login", "anon");
        //放开登录的接口
        chainMap.put("/userLogin", "anon");
        //放开跳转注册页面的接口
        chainMap.put("/register", "anon");
        //放开注册的接口
        chainMap.put("/registerUser", "anon");
        //这个限制需要在最后put
        chainMap.put("/*", "authc");
        //为子级目录也控制访问
        chainMap.put("/**", "authc");
        //设置登录页
        factoryBean.setLoginUrl("/login");
        //登录成功跳转
        factoryBean.setSuccessUrl("/success");
        //登录无权跳转
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
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean--->实测没他还真不行
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
