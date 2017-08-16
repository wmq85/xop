package com.mqoo.platform.xop.common.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

/**
 * WebRequestContextHolder
 * <p>
 * 使用ThreadLocal 缓存访问线程信息：
 * 保护appKey、token、clientIp等<br>
 * 在线程结束时务必调用WebRequestContextHolder#remove方法进行清除
 *
 *@author mingqi.wang
 */
public abstract class WebRequestContextHolder {
   
    public static final String APP_KEY = "appKey";
    public static final String TOKEN = "token";
    public static final String USER_ID = "userId";
    public static final String CLIENT_IP = "clientIp";
    public static final String EXT = "ext";
    private static final ThreadLocal<Map<Object, Object>> resources =
                    new WebRequestContextHolder.InheritableThreadLocalMap<>();

    /**
     * 获取app key
     * @return
     */
    public static String getAppKey() {
        return (String) get(APP_KEY);
    }

    /**
     * 设置appKey
     * @param appKey
     */
    public static void setAppKey(String appKey) {
        put(APP_KEY, appKey);
    }
    
    /**
     * 获取token
     * @return
     */
    public static String getToken() {
        return (String) get(TOKEN);
    }

    /**
     * 设置token
     * @param token
     */
    public static void setToken(String token) {
        put(TOKEN, token);
    }
    
    /**
     * 获取用户ID
     * @return
     */
    public static String getUserId() {
        return (String) get(USER_ID);
    }

    /**
     * 设置用户ID
     * @param userId
     */
    public static void setUserId(String userId) {
        put(USER_ID, userId);
    }
    
    /**
     * 获取ip
     * @return
     */
    public static String getClientIp() {
        return (String) get(CLIENT_IP);
    }

    /**
     * 设置ip
     * @param clientIp
     */
    public static void setClientIp(String clientIp) {
        put(CLIENT_IP, clientIp);
    }

    public static Map<String, Object> getExt() {
        return (Map<String, Object>) get(EXT);
    }

    public static void setExt(Map<String, Object> info) {
        put(EXT, info);
    }

    private WebRequestContextHolder() {}

    public static Map<Object, Object> getResources() {
        return resources.get() == null ? Collections.emptyMap() : new HashMap<>(resources.get());
    }

    public static void setResources(Map<Object, Object> newResources) {
        if (!MapUtils.isEmpty(newResources)) {
            ensureResourcesInitialized();
            ((Map<Object, Object>) resources.get()).clear();
            ((Map<Object, Object>) resources.get()).putAll(newResources);
        }
    }

    private static Object getValue(Object key) {
        Map perThreadResources = resources.get();
        return perThreadResources != null ? perThreadResources.get(key) : null;
    }

    private static void ensureResourcesInitialized() {
        if (resources.get() == null) {
            resources.set(new HashMap<>());
        }

    }

    public static Object get(Object key) {
        Object value1 = getValue(key);
        return value1;
    }

    public static void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        } else if (value == null) {
            remove(key);
        } else {
            ensureResourcesInitialized();
            ((Map<Object, Object>) resources.get()).put(key, value);

        }
    }

    public static Object remove(Object key) {
    	Map<Object, Object> perThreadResources = resources.get();
        Object value = perThreadResources != null ? perThreadResources.remove(key) : null;
        return value;
    }

    public static void remove() {
        resources.remove();
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>>
                    extends InheritableThreadLocal<Map<Object, Object>> {
        private InheritableThreadLocalMap() {}

        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            return parentValue != null ? (Map) ((HashMap) parentValue).clone() : null;
        }
    }
}