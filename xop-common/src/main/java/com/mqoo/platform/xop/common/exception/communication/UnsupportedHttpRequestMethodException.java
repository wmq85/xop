package com.mqoo.platform.xop.common.exception.communication;

import com.mqoo.platform.xop.common.exception.BaseException;

/**
 * Unsupported Http Request Method Exception.
 *
 * @author mingqi.wang
 * @since 2017/7/6
 */
public class UnsupportedHttpRequestMethodException extends BaseException {

    public static final String CODE = "E1100";
    private static final String MSG = "The specified HTTP request method is not supported.";

    public UnsupportedHttpRequestMethodException(String devMsg) {
        super(CODE, MSG);
        setDevMsg(devMsg);
    }

    public UnsupportedHttpRequestMethodException() {
        this(null);
    }

}
