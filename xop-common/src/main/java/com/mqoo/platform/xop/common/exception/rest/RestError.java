package com.mqoo.platform.xop.common.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

/**
 * Rest Error 
 * <p>
 * 最终根据此类序列化错误报文
 * 
 * @author mingqi.wang
 * @since 2017/7/6
 */
public class RestError {
	public static final String DEFAULT_CODE = "E0000";
	
    private final HttpStatus status;
    private final String errCode;
    private final String errMsg;
    private final String devMsg;
    private final String moreInfoUrl;
    private final Throwable throwable;
    private String requestId;

    public RestError(HttpStatus status, String errCode, String errMsg, String devMsg,
            String moreInfoUrl, Throwable throwable, String requestId) {
        if (status == null) {
            throw new NullPointerException("HttpStatus argument cannot be null.");
        }
        this.status = status;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.devMsg = devMsg;
        this.moreInfoUrl = moreInfoUrl;
        this.throwable = throwable;
        this.requestId = requestId;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getDevMsg() {
        return devMsg;
    }

    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof RestError) {
            RestError re = (RestError) o;
            // @formatter:off
            return
                ObjectUtils.nullSafeEquals(getStatus(), re.getStatus())
                    && ObjectUtils.nullSafeEquals(getErrCode(), re.getErrCode())
                    && ObjectUtils.nullSafeEquals(getErrMsg(), re.getErrMsg())
                    && ObjectUtils.nullSafeEquals(getDevMsg(), re.getDevMsg())
                    && ObjectUtils.nullSafeEquals(getMoreInfoUrl(), re.getMoreInfoUrl())
                    && ObjectUtils.nullSafeEquals(getThrowable(), re.getThrowable())
                    && ObjectUtils.nullSafeEquals(getRequestId(), re.getRequestId());
            // @formatter:on
        }

        return false;
    }

    @Override
    public int hashCode() {
        //noinspection ThrowableResultOfMethodCallIgnored
        return ObjectUtils.nullSafeHashCode(
                new Object[] {getStatus(), getErrCode(), getErrMsg(), getDevMsg(), getMoreInfoUrl(),
                        getThrowable(), getRequestId()});
    }

    public String toString() {
        //noinspection StringBufferReplaceableByString
        return new StringBuilder().append(getStatus().value()).append(" (")
                .append(getStatus().getReasonPhrase()).append(" )").toString();
    }

    /**
     * RestError builder
     */
    public static class Builder {

        private HttpStatus status;
        private String errCode;
        private String errMsg;
        private String devMsg;
        private String moreInfoUrl;
        private String requestId;
        private Throwable throwable;

        public Builder() {
        }

        public Builder setStatus(int statusCode) {
            this.status = HttpStatus.valueOf(statusCode);
            return this;
        }

        public Builder setStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder setErrCode(String errCode) {
            this.errCode = errCode;
            return this;
        }

        public Builder setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        public Builder setDevMsg(String devMsg) {
            this.devMsg = devMsg;
            return this;
        }

        public Builder setMoreInfoUrl(String moreInfoUrl) {
            this.moreInfoUrl = moreInfoUrl;
            return this;
        }

        public Builder setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public Builder setRequestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public RestError build() {
            if (this.status == null) {
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            if (this.errCode == null) {
                this.errCode = DEFAULT_CODE;
            }
            return new RestError(this.status, this.errCode, this.errMsg, this.devMsg,
                    this.moreInfoUrl, this.throwable, this.requestId);
        }
    }
}
