package com.mqoo.xop.starter.wechat.auth.web;

import javax.servlet.http.HttpServletRequest;

import com.mqoo.platform.xop.common.util.HttpUtil;

public class SessionUtil {
    public final static String USER_ID = "userId";
    public final static String USER = "user";

    public static void putValue(String key, Object value) {
        HttpServletRequest request = HttpUtil.getCurrentHttpRequest();
        request.getSession().setAttribute(key, value);
    }

    public static Object getValue(String key) {
        HttpServletRequest request = HttpUtil.getCurrentHttpRequest();
        return request.getSession().getAttribute(key);
    }

    public static void putUserId(String userId) {
        putValue(USER_ID, userId);
    }

    public static String getUserId() {
        return (String) getValue(USER_ID);
    }
}
