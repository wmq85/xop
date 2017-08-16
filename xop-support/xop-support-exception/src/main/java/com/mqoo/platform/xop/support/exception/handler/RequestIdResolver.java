package com.mqoo.platform.xop.support.exception.handler;

import org.springframework.web.context.request.ServletWebRequest;

public interface RequestIdResolver {

    /**
     * Returns a request id to render as the response error requestId field.
     *
     * @param request current {@link ServletWebRequest} that can be used to obtain the source request/response pair.
     * @param handler the executed handler, or <code>null</code> if none chosen at the time of the exception
     *                (for example, if multipart resolution failed)
     * @param ex      the exception that was thrown during handler execution
     * @return a resolved request id to render as the response error message request id field or <code>null</code> for default
     * processing
     */
    String resolveRequestId(ServletWebRequest request, Object handler, Exception ex);
}
