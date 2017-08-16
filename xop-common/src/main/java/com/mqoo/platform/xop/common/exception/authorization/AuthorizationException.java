package com.mqoo.platform.xop.common.exception.authorization;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;
import com.mqoo.platform.xop.common.exception.general.GeneralException;

/**
 * 基础授权认证异常
 * 
 * @author mingqi.wang
 */
@ExceptionResponseInfo(errCode = AuthorizationException.CODE)
public class AuthorizationException extends GeneralException {

    public static final String CODE = "E10000";

    public AuthorizationException(String message) {
        super(CODE, message);
    }

    protected AuthorizationException(String errCode, String message) {
        super(errCode, message);
    }

    protected AuthorizationException(String errCode, String message, Object... params) {
        super(errCode, message, params);
    }
}
