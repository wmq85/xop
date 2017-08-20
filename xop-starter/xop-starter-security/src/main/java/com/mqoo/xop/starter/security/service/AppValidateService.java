package com.mqoo.xop.starter.security.service;

/**
 * app 验证接口
 * 
 * @author mingqi.wang
 * @since 2017/7/10
 */
public interface AppValidateService {

    /**
     * app 是否可用
     * 
     * @param appKey
     * @return
     */
    public boolean available(String appKey);

    /**
     * app 是否存在
     * 
     * @param appKey
     * @return
     */
    public boolean exists(String appKey);

    /**
     * app 签名验证
     * 
     * @param appKey
     * @param signStr 签名字符串
     * @param signature 签名值
     * @return
     */
    public boolean signVerify(String appKey, String signStr, String signature);

    /**
     * 获取app public key
     * 
     * @param appKey
     * @return
     */
    public String getAppPublicKey(String appKey);

    /**
     * 获取app private key
     * 
     * @param appKey
     * @return
     */
    public String getAppPrivateKey(String appKey);
}
