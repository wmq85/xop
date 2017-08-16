package com.mqoo.platform.xop.common.web.api;

/**
 * Constants for web components
 *
 */
public abstract class Constants {

    private Constants() {
    }

    public static abstract class HttpHeader {
        private HttpHeader() {
        }
        public static final String BEARER ="Bearer ";
        public static final String HEADER_REQUEST_ID = "X-Request-Id";
        public static final String HEADER_REQUEST_TIMESTAMP = "X-Request-Timestamp";
        public static final String HEADER_REQUEST_SIGNATURE = "X-Request-Signature";
        public static final String HEADER_CLIENT_AGENT = "X-Client-Agent";
        public static final String HEADER_API_VERSION = "X-Api-Version";
        public static final String HEADER_AUTHORIZATION ="Authorization";
    }

}
