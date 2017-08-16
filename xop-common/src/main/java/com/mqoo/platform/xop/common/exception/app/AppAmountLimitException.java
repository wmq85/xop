package com.mqoo.platform.xop.common.exception.app;

import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * app 调用次数限制
 * 
 * @author mingqi.wang
 */
@ExceptionResponseInfo(errCode = AppAmountLimitException.CODE, errMsg = "The amount of application exceeded the limitation.")
public class AppAmountLimitException extends BaseException {

    public static final String CODE = "E3400";

    public AppAmountLimitException(String message) {
        super(CODE, message);
    }

    public AppAmountLimitException(String message, Object... params) {
        super(CODE, message, params);
    }

}
