package com.mqoo.platform.xop.common.exception.security;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;
import com.mqoo.platform.xop.common.exception.general.GeneralException;

/**
 * 非法请求 Exception .
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
@ExceptionResponseInfo(errCode = IllegalRequestException.CODE)
public class IllegalRequestException extends SecurityException {

    public static final String CODE = "E3101";

    public IllegalRequestException(String message) {
        super(CODE, message);
    }

    protected IllegalRequestException(String errCode, String message) {
        super(errCode, message);
    }

    protected IllegalRequestException(String errCode, String message, Object... params) {
        super(errCode, message, params);
    }
}
