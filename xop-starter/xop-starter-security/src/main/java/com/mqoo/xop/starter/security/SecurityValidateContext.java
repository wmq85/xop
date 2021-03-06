package com.mqoo.xop.starter.security;

import org.apache.commons.lang3.StringUtils;

import com.mqoo.platform.xop.common.web.BufferedRequestWrapper;
import com.mqoo.platform.xop.common.web.api.Constants;

/**
 * 安全验证上下文
 * <p>
 * 保存有appKey,signature,timestamp,token,httpRequest等请求信息
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public class SecurityValidateContext {
    private String appKey;
    private String signature;
    private String timestamp;
    /**
     * token 可以从头部或者query string中获取
     */
    private String token;
    private BufferedRequestWrapper rawRequest;

    public SecurityValidateContext(BufferedRequestWrapper rawRequest) {
        this.rawRequest = rawRequest;
        //
        String time = rawRequest.getHeader(Constants.HttpHeader.HEADER_REQUEST_TIMESTAMP);
        String signature = rawRequest.getHeader(Constants.HttpHeader.HEADER_REQUEST_SIGNATURE);
        String bearer = rawRequest.getHeader(Constants.HttpHeader.HEADER_AUTHORIZATION);
        String appKey = StringUtils.replace(bearer, Constants.HttpHeader.BEARER, "");
        //
        String token = rawRequest.getHeader(Constants.HttpHeader.HEADER_REQUEST_TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = rawRequest.getParameter(Constants.HttpParam.ACCESS_TOKEN);
        }
        this.timestamp = time;
        this.appKey = appKey;
        this.signature = signature;
        this.token = token;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public BufferedRequestWrapper getRawRequest() {
        return rawRequest;
    }

    public void setRawRequest(BufferedRequestWrapper rawRequest) {
        this.rawRequest = rawRequest;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
