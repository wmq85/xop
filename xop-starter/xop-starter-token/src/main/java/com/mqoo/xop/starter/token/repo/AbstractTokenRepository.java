package com.mqoo.xop.starter.token.repo;

import java.util.UUID;

import com.mqoo.xop.starter.token.session.TokenSession;

/**
 * tokenSession repository
 * 
 * @author mingqi.wang
 * @since 2018/8/18
 */
public abstract class AbstractTokenRepository {

    private Long tokenTimeoutSeconds;

    /**
     * 生成 token
     * 
     * @return token
     */
    public String newToken() {
        String randowStr = UUID.randomUUID().toString().replaceAll("-", "");
        return randowStr;
    }

    /**
     * 创建TokenSession
     * 
     * @return tokenSession
     */
    public abstract TokenSession create();

    /**
     * 更新TokenSession
     * 
     * @param tokenSession tokenSession
     */
    public abstract void merge(TokenSession tokenSession);

    /**
     * 获取tokenSession 对象
     * 
     * @param token
     * @return tokenSession
     */
    public abstract TokenSession get(String token);

    /**
     * 删除token
     * 
     * @param token
     */
    public abstract void remove(String token);

    /**
     * 刷新 token
     * 
     * @param token
     * @return
     */
    public abstract TokenSession touch(String token);

    public Long getTokenTimeoutSeconds() {
        return tokenTimeoutSeconds;
    }

    public void setTokenTimeoutSeconds(Long tokenTimeoutSeconds) {
        this.tokenTimeoutSeconds = tokenTimeoutSeconds;
    }
}
