package com.mqoo.platform.xop.common.exception.authorization;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * Forbidden Exception
 */
// formatter:off
@ExceptionResponseInfo(
    errCode = ForbiddenException.CODE,
    errMsg = "You are not allowed to access this resource.",
    devMsg = "Equivalent to HTTP 403 Forbidden error, indicates that the server understood the request but refuses to authorize it.",
    moreInfoUrl = "https://tools.ietf.org/html/rfc7231#section-6.5.3"
)
// formatter:on
public class ForbiddenException extends AuthorizationException {

    public static final String CODE = "E10003";

    public ForbiddenException(String message) {
        super(CODE, message);
    }

    public ForbiddenException(String message, Object... params) {
        super(CODE, message, params);
    }
}
