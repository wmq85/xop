package com.mqoo.platform.xop.common.exception.communication;

import com.mqoo.platform.xop.common.exception.BaseException;

/**
 * Request timeout.
 *
 * @author mingqi.wang
 * @since 2017/7/6
 */
public class RequestTimeoutException extends BaseException {

    public static final String CODE = "E1102";
    private static final String MSG = "Request timeout.";

    public RequestTimeoutException(String devMsg) {
        super(CODE, MSG);
        setDevMsg(devMsg);
    }

    public RequestTimeoutException() {
        this(null);
    }

}
