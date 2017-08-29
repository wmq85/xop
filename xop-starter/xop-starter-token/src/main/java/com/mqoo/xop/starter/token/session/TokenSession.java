package com.mqoo.xop.starter.token.session;

import java.io.Serializable;

/**
 * token session 接口
 * 
 * @author mingqi.wang
 * @since 2017/8/18
 *
 */
public interface TokenSession extends Serializable {
    /**
     * 获取sessionId
     * 
     * @return
     */
    String getToken();

    /**
     * 获取session 属性
     * 
     * @param key 属性key
     * @return Attr
     */
    <T extends Serializable> T getAttr(String key);

    /**
     * 设置session 属性
     * 
     * @param val 属性值
     */
    void setAttr(String key, Serializable val);

    /**
     * 获取过期时长
     * 
     * @return 过期时长，以秒为单位
     */
    long getTtl();

    /**
     * 过期时间
     * 
     * @return 过期时间戳
     */
    long expiredAt();

    /**
     * 刷新过期时间
     */
    public void refreshExpiredAt();

    /**
     * 获取token session
     * 
     * @return
     */
    public TokenSession getObject();
}
