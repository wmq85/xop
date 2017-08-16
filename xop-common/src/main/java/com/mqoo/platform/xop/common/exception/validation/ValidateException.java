package com.mqoo.platform.xop.common.exception.validation;

import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 参数验证基础异常
 * @author mingqi.wang
 *
 */
@ExceptionResponseInfo(errCode = ValidateException.CODE)
public class ValidateException extends BaseException {

    public static final String CODE = "E2000";

    public ValidateException(String message) {
        super(CODE, message);
    }

    protected ValidateException(String errCode, String message) {
        super(errCode, message);
    }

    protected ValidateException(String errCode, String message, Object... params) {
        super(errCode, message, params);
    }
}
