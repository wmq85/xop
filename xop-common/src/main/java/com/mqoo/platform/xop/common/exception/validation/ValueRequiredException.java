package com.mqoo.platform.xop.common.exception.validation;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 值必须异常
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
@ExceptionResponseInfo(errCode = ValueRequiredException.CODE)
public class ValueRequiredException extends ValidateException {

    public static final String CODE = "E2006";

    public ValueRequiredException(String message) {
        super(CODE, message);
    }

    public ValueRequiredException(String message, Object... params) {
        super(CODE, message, params);
    }

}
