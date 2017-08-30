package com.mqoo.xop.starter.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用来标注需要进行token检查的方法
 * 
 * @author mingqi.wang
 * @since 2017/8/18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AuthorizationToken {
    boolean value() default true;
}
