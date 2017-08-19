package com.mqoo.xop.starter.security.service;

import java.util.HashMap;
import java.util.Map;

import com.mqoo.platform.xop.common.util.encryption.Sign;
import com.mqoo.xop.starter.security.SecurityProperties;

/**
 * 简单app service 验证实现类
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public class SampleAppValidateService implements AppValidateService {
    private SecurityProperties securityProperties;

    private Map<String, AppModel> apps = new HashMap<>();

    public SampleAppValidateService(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        if (securityProperties.getApps() != null) {
            for (AppModel appModel : securityProperties.getApps()) {
                apps.put(appModel.getAppId().trim(), appModel);
            }
        }
    }

    @Override
    public boolean available(String appKey) {
        return apps.containsKey(appKey);
    }

    @Override
    public boolean exists(String appKey) {
        return apps.containsKey(appKey);
    }

    @Override
    public boolean signVerify(String appKey, String signStr, String signature) {
        String publicKey = getAppPublicKey(appKey);
        boolean verify = Sign.verify(publicKey, signStr, signature);
        return verify;
    }

    @Override
    public String getAppPublicKey(String appKey) {
        String publicKey = apps.get(appKey).getPublicKey();
        return publicKey;
    }

    @Override
    public String getAppPrivateKey(String appKey) {
        String privateKey = apps.get(appKey).getPrivateKey();
        return privateKey;
    }
}
