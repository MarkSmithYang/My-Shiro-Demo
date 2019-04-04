package com.yb.shiro.model;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Max;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/10/9
 */
@Aspect
@Configuration
public class Anno {

    @Pointcut
    public void point(){

    }

}
