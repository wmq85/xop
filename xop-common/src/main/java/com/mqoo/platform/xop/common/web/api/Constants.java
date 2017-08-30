package com.mqoo.platform.xop.common.web.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * Constants for web components
 *
 */
public abstract class Constants {

    private Constants() {}

    public static abstract class HttpHeader {
        private HttpHeader() {}

        public static final String BEARER = "Bearer ";
        public static final String HEADER_REQUEST_ID = "x-request-id";
        public static final String HEADER_REQUEST_TIMESTAMP = "x-timestamp";
        public static final String HEADER_REQUEST_SIGNATURE = "x-signature";
        public static final String HEADER_REQUEST_TOKEN = "x-token";
        public static final String HEADER_CLIENT_AGENT = "x-client-agent";
        public static final String HEADER_API_VERSION = "x-api-version";
        public static final String HEADER_AUTHORIZATION = "Authorization";
    }

    public static abstract class HttpParam {
        private HttpParam() {}

        public static final String ACCESS_TOKEN = "access_token";
    }

    /**
     * 从http request 中获取token
     * 
     * @param request http request
     * @return token
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        //
        String token = request.getHeader(Constants.HttpHeader.HEADER_REQUEST_TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(Constants.HttpParam.ACCESS_TOKEN);
        }
        return token;
    }


    /**
     * 从http request 中获取timestamp
     * 
     * @param request http request
     * @return timestamp
     */
    public static String getTimestampFromRequest(HttpServletRequest request) {
        return request.getHeader(Constants.HttpHeader.HEADER_REQUEST_TIMESTAMP);
    }

    /**
     * 从http request 中获取signature
     * 
     * @param request http request
     * @return signature
     */
    public static String getSignatureFromRequest(HttpServletRequest request) {
        return request.getHeader(Constants.HttpHeader.HEADER_REQUEST_SIGNATURE);
    }

    /**
     * 从http request 中获取Authorization
     * 
     * @param request http request
     * @return Authorization
     */
    public static String getAuthorizationFromRequest(HttpServletRequest request) {
        return request.getHeader(Constants.HttpHeader.HEADER_AUTHORIZATION);
    }

    /**
     * 从http request 中获取appKey
     * 
     * @param request http request
     * @return appId
     */
    public static String getAppKeyFromRequest(HttpServletRequest request) {
        String bearer = getAuthorizationFromRequest(request);
        String appKey = StringUtils.replace(bearer, Constants.HttpHeader.BEARER, "");
        return StringUtils.trimToEmpty(appKey);
    }

}
