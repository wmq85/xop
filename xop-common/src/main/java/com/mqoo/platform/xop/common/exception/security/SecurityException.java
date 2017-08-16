package com.mqoo.platform.xop.common.exception.security;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;
import com.mqoo.platform.xop.common.exception.general.GeneralException;

/**
 * Base Exception for all security category.
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
@ExceptionResponseInfo(errCode = SecurityException.CODE)
public class SecurityException extends GeneralException {

    public static final String CODE = "E3100";

    public SecurityException(String message) {
        super(CODE, message);
    }

    protected SecurityException(String errCode, String message) {
        super(errCode, message);
    }

    protected SecurityException(String errCode, String message, Object... params) {
        super(errCode, message, params);
    }
}
