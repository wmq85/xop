package com.mqoo.platform.xop.common.exception.validation;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 类型不正确异常
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
@ExceptionResponseInfo(errCode = TypeInvalidException.CODE)
public class TypeInvalidException extends ValidateException {

    public static final String CODE = "E2004";

    public TypeInvalidException(String message) {
        super(CODE, message);
    }

    public TypeInvalidException(String message, Object... params) {
        super(CODE, message, params);
    }

}
