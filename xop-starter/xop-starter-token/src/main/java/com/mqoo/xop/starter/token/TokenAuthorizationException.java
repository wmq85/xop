package com.mqoo.xop.starter.token;

import org.springframework.http.HttpStatus;

import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * token 验证异常
 * 
 * @author mingqi.wang
 * @since 2017/7/6
 */
// @formatter:off
@ExceptionResponseInfo(
        status=HttpStatus.UNAUTHORIZED,
        errCode = TokenAuthorizationException.CODE,
        errMsg = "token expired or need to login.")
// @formatter:on
public class TokenAuthorizationException extends BaseException {

    public static final String CODE = "030000";

    public TokenAuthorizationException() {
        this(null);
    }

    public TokenAuthorizationException(String message) {
        super(CODE, message);
    }

}
