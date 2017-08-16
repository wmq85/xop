
package com.mqoo.platform.xop.common.exception.validation;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 值不支持异常
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
@ExceptionResponseInfo(errCode = ValueUnsupportedException.CODE)
public class ValueUnsupportedException extends ValidateException {

    public static final String CODE = "E2003";

    public ValueUnsupportedException(String message) {
        super(CODE, message);
    }

    public ValueUnsupportedException(String message, Object... params) {
        super(CODE, message, params);
    }

}
