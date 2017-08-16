package com.mqoo.platform.xop.common.exception;

import org.springframework.core.NestedRuntimeException;

import java.text.MessageFormat;

/**
 * Base exception.
*
 * @author mingqi.wang
 * @since 2017/7/6
 */
public abstract class BaseException extends NestedRuntimeException implements ExceptionDetail {

    /**
     * 响应的HTTP状态码； 为了兼容客户端某些工具无法获取HTTP状态码；
     */
    private int httpStatusCode = 500;

    /**
     * 错误码，可以用于获取此错误的相关信息；
     */
    private String errCode;

    /**
     * 一种清晰和原始的技术细节消息，用于开发者调试API；
     */
    private String devMsg;

    /**
     * 用于获取该错误更多信息的URL地址；
     */
    private String moreInfoUrl = null;

    /**
     * 是否显示获取该错误更多信息的URL地址；
     */
    private boolean disableMoreInfoUrl = false;

    protected BaseException(String errCode, String message) {
        this(errCode, message, null, message, "");
    }

    protected BaseException(String errCode, String message, Object... params) {
        this(errCode, MessageFormat.format(message.replaceAll("'", "''"), params));
    }

    protected BaseException(String errCode, String message, String moreInfoUrl) {
        this(errCode, message, null, message, moreInfoUrl);
    }

    protected BaseException(String errCode, String message, String devMsg, String moreInfoUrl) {
        this(errCode, message, null, devMsg, moreInfoUrl);
    }

    protected BaseException(String errCode, String message, Throwable cause, String devMsg,
            String moreInfoUrl) {
        super(message, cause);

        this.errCode = errCode;

        if (null == devMsg) {
            this.devMsg = message;
        } else {
            this.devMsg = devMsg;
        }

        if (null != moreInfoUrl) {
            this.moreInfoUrl = moreInfoUrl;
        }
    }

    protected BaseException(String errCode, Throwable cause, String devMsg, String moreInfoUrl) {
        this(errCode, cause.getMessage(), cause, devMsg, moreInfoUrl);
    }

    public String getDevMsg() {
        return devMsg;
    }

    /**
     * Note: Not allow set <code>null</code> to devMsg.
     *
     * @param devMsg Message for developers
     */
    public void setDevMsg(String devMsg) {
        if (null == devMsg) {
            return;
        }

        this.devMsg = devMsg;
    }

    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }

    public void setMoreInfoUrl(String moreInfoUrl) {
        this.moreInfoUrl = moreInfoUrl;
    }

    public String getErrCode() {
        return errCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public boolean isDisableMoreInfoUrl() {
        return disableMoreInfoUrl;
    }

    public void setDisableMoreInfoUrl(boolean disableMoreInfoUrl) {
        this.disableMoreInfoUrl = disableMoreInfoUrl;
    }
}
