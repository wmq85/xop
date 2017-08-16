
package com.mqoo.platform.xop.common.exception.general;

import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.common.exception.annotation.ExceptionResponseInfo;

/**
 * 通用异常
*
 * @author mingqi.wang
 * @since 2017/7/6
 */
@ExceptionResponseInfo(errCode = GeneralException.CODE)
public class GeneralException extends BaseException {

    public static final String CODE = "E0000";

    public GeneralException(String message) {
        super(CODE, message);
    }

    public GeneralException(String errCode, String message) {
        super(errCode, message);
    }

    protected GeneralException(String errCode, String message, Object... params) {
        super(errCode, message, params);
    }

    protected GeneralException(String errCode, String message, String moreInfoUrl) {
        super(errCode, message, moreInfoUrl);
    }

    protected GeneralException(String errCode, String message, String devMsg, String moreInfoUrl) {
        super(errCode, message, devMsg, moreInfoUrl);
    }

    protected GeneralException(String errCode, String message, Throwable cause, String devMsg,
            String moreInfoUrl) {
        super(errCode, message, cause, devMsg, moreInfoUrl);
    }

    protected GeneralException(String errCode, Throwable cause, String devMsg, String moreInfoUrl) {
        super(errCode, cause, devMsg, moreInfoUrl);
    }
}
