package com.mqoo.platform.xop.common.exception.validation;

import org.springframework.http.HttpStatus;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 对象无法找到异常
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
//@formatter:off
@ExceptionResponseInfo(
    status = HttpStatus.NOT_FOUND,
    errCode = NotFoundException.CODE
)
//@formatter:on
public class NotFoundException extends ValidateException {

    public static final String CODE = "E2005";

    public NotFoundException(String message) {
        super(CODE, message);
    }

    public NotFoundException(String message, Object... params) {
        super(CODE, message, params);
    }

}
