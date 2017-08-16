package com.mqoo.platform.xop.common.exception.validation;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 值不正确异常
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
@ExceptionResponseInfo(errCode = ValueInvalidException.CODE)
public class ValueInvalidException extends ValidateException {

    public static final String CODE = "E2002";

    public ValueInvalidException(String message) {
        super(CODE, message);
    }

    public ValueInvalidException(String message, Object... params) {
        super(CODE, message, params);
    }

}
