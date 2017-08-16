package com.mqoo.platform.xop.common.exception.app;

import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * app 不可用
 * 
 * @author mingqi.wang
 */
@ExceptionResponseInfo(errCode = AppDisabledException.CODE, errMsg = "The application is disabled.")
public class AppDisabledException extends BaseException {

    public static final String CODE = "E3401";

    public AppDisabledException(String message) {
        super(CODE, message);
    }

    public AppDisabledException(String message, Object... params) {
        super(CODE, message, params);
    }

}
