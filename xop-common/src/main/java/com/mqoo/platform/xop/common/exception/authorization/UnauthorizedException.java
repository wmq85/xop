package com.mqoo.platform.xop.common.exception.authorization;

import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * Unauthorized Exception
 */
// formatter:off
@ExceptionResponseInfo(
    errCode = UnauthorizedException.CODE,
    errMsg = "Unauthorized, you must login first.",
    devMsg = "Equivalent to HTTP 401 Unauthorized error, indicates that the request has not been applied because it lacks valid authentication credentials for the target resource.",
    moreInfoUrl = "https://tools.ietf.org/html/rfc7235#section-3.1"
)
// formatter:on
public class UnauthorizedException extends AuthorizationException {

    public static final String CODE = "E10001";

    public UnauthorizedException(String message) {
        super(CODE, message);
    }

    public UnauthorizedException() {
        this(null);
    }
}
