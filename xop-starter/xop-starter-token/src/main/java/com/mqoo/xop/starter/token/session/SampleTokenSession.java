package com.mqoo.xop.starter.token.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 默认token session 实现
 * 
 * @author mingqi.wang
 * @since 2017/8/18
 */
public class SampleTokenSession implements TokenSession {
    /**
     * 
     */
    private static final long serialVersionUID = 8055687944498052758L;
    /**
     * token
     */
    private String token;
    /**
     * time to live, in seconds
     */
    private long ttl;
    /**
     * expired at
     */
    private long expiredAt;

    private Map<String, Serializable> attrs = new HashMap<>();

    public SampleTokenSession(String token, long ttl) {
        this.token = token;
        this.ttl = ttl;
    }

    @Override
    public void refreshExpiredAt() {
        long now = System.currentTimeMillis();
        expiredAt = now + ttl * 1000;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public <T extends Serializable> T getAttr(String key) {
        return (T) attrs.get(key);
    }

    @Override
    public void setAttr(String key, Serializable val) {
        attrs.put(key, val);
    }

    @Override
    public long getTtl() {
        return ttl;
    }

    @Override
    public long expiredAt() {
        return expiredAt;
    }

    @Override
    @JsonIgnore
    public TokenSession getObject() {
        return this;
    }

}
