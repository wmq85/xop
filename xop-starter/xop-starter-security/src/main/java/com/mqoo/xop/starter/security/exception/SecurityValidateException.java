package com.mqoo.xop.starter.security.exception;

import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 安全验证异常
 * 
 * @author mingqi.wang
 * @since 2017/7/6
 */
// @formatter:off
@ExceptionResponseInfo(errCode = SecurityValidateException.CODE, errMsg = "illegal api access")
// @formatter:on
public class SecurityValidateException extends BaseException {

    public static final String CODE = "E1000";

    public SecurityValidateException(String message) {
        super(CODE, message);
    }

    public SecurityValidateException(String errCode, String message) {
        super(errCode, message);
    }

    public SecurityValidateException(String errCode, String message, Object... params) {
        super(errCode, message, params);
    }

}
