package com.mqoo.platform.xop.common.exception.general;

import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 服务不可用
 * 
 * @author mingqi.wang
 * @since 2017/7/6
 */
//@formatter:off
@ExceptionResponseInfo(
    errCode = ServiceNotAvailableException.CODE,
    errMsg = "Service not available now."
)
//@formatter:on
public class ServiceNotAvailableException extends BaseException {

    public static final String CODE = "E0001";

    public ServiceNotAvailableException(String message) {
        super(CODE, message);
    }

    public ServiceNotAvailableException(String errCode, String message) {
        super(errCode, message);
    }

    protected ServiceNotAvailableException(String errCode, String message, Object... params) {
        super(errCode, message, params);
    }

    protected ServiceNotAvailableException(String errCode, String message, String moreInfoUrl) {
        super(errCode, message, moreInfoUrl);
    }

    protected ServiceNotAvailableException(String errCode, String message, String devMsg, String moreInfoUrl) {
        super(errCode, message, devMsg, moreInfoUrl);
    }

    protected ServiceNotAvailableException(String errCode, String message, Throwable cause, String devMsg,
            String moreInfoUrl) {
        super(errCode, message, cause, devMsg, moreInfoUrl);
    }

    protected ServiceNotAvailableException(String errCode, Throwable cause, String devMsg, String moreInfoUrl) {
        super(errCode, cause, devMsg, moreInfoUrl);
    }
}
