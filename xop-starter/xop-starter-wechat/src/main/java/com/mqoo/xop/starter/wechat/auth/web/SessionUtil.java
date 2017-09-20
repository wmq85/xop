package com.mqoo.xop.starter.wechat.auth.web;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public final static String USER_ID = "userId";
    public final static String USER = "user";

    public static void putValue(String key, Object value, HttpServletRequest request) {
        request.getSession().setAttribute(key, value);
    }

    public static Object getValue(String key, HttpServletRequest request) {
        return request.getSession().getAttribute(key);
    }

    // public static void putUser(MerchantWechatUser user, HttpServletRequest request) {
    // putValue(USER, user, request);
    // }
    //
    // public static MerchantWechatUser getUser(HttpServletRequest request) {
    // return (MerchantWechatUser) getValue(USER, request);
    // }

    public static void putUserId(String userId, HttpServletRequest request) {
        putValue(USER_ID, userId, request);
    }

    public static String getUserId(HttpServletRequest request) {
        return (String) getValue(USER_ID, request);
    }
}
