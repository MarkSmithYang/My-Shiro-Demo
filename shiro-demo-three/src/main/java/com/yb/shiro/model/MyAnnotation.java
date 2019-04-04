package com.yb.shiro.model;

import java.lang.annotation.*;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/10/9
 */
@Documented
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    String value() default "";
}
