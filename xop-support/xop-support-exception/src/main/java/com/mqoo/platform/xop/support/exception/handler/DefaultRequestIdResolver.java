package com.mqoo.platform.xop.support.exception.handler;

import org.springframework.web.context.request.ServletWebRequest;

import com.mqoo.platform.xop.common.web.api.Constants;

/**
 * Resolve request id from http request header by key .
 *
 */
public class DefaultRequestIdResolver implements RequestIdResolver {

    @Override
    public String resolveRequestId(ServletWebRequest request, Object handler, Exception ex) {
        return request.getHeader(Constants.HttpHeader.HEADER_REQUEST_ID);
    }
}
