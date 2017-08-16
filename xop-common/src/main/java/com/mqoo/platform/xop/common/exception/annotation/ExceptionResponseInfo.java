package com.mqoo.platform.xop.common.exception.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;

import com.mqoo.platform.xop.common.exception.general.GeneralException;

/**
 * 异常信息模板注解
 * <p>
 * 通过此注解生成异常响应报文
 * 
 * @author mingqi.wang
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionResponseInfo {

    /**
     * Alias for {@link #status}.
     */
    @AliasFor("status") HttpStatus value() default HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * The <em>status</em> code to use for the response.
     */
    @AliasFor("value")
    HttpStatus status() default HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * 错误码，可以用于获取此错误的相关信息；
     */
    String errCode() default GeneralException.CODE;

    /**
     * The <em>errMsg</em> to be used for the error message.
     */
    String errMsg() default "";

    /**
     * 一种清晰和原始的技术细节消息，用于开发者调试API；
     */
    String devMsg() default "";

    /**
     * 用于获取该错误更多信息的URL地址；
     */
    String moreInfoUrl() default "";

}
