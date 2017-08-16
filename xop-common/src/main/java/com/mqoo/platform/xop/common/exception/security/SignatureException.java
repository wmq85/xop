package com.mqoo.platform.xop.common.exception.security;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;
import com.mqoo.platform.xop.common.exception.general.GeneralException;

/**
 * 签名错误  Exception .
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
@ExceptionResponseInfo(errCode = SignatureException.CODE)
public class SignatureException extends SecurityException {

    public static final String CODE = "E3102";

    public SignatureException(String message) {
        super(CODE, message);
    }

    protected SignatureException(String errCode, String message) {
        super(errCode, message);
    }

    protected SignatureException(String errCode, String message, Object... params) {
        super(errCode, message, params);
    }
}
