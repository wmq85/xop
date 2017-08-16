package com.mqoo.platform.xop.common.exception.validation;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 值重复异常
 * 
* @author mingqi.wang
 * @since 2017/7/10
 */
@ExceptionResponseInfo(errCode = ValueDuplicatedException.CODE)
public class ValueDuplicatedException extends ValidateException {

    public static final String CODE = "E2001";

    public ValueDuplicatedException(String message) {
        super(CODE, message);
    }

    public ValueDuplicatedException(String message, Object... params) {
        super(CODE, message, params);
    }

}
