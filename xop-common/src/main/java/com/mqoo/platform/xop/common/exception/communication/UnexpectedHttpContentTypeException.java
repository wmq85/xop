package com.mqoo.platform.xop.common.exception.communication;

import com.mqoo.platform.xop.common.exception.BaseException;

/**
 * Unexpected Http Content-Type Exception
 *
 * @author mingqi.wang
 * @since 2017/7/6
 */
public class UnexpectedHttpContentTypeException extends BaseException {

    public static final String CODE = "E1101";
    private static final String MSG = "Bad request 'Content-Type'. This error will specify what the expected value is.";

    public UnexpectedHttpContentTypeException(String devMsg) {
        super(CODE, MSG);
        setDevMsg(devMsg);
    }

    public UnexpectedHttpContentTypeException() {
        this(null);
    }
}
