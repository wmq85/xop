package com.mqoo.platform.xop.support.security.service;

import com.mqoo.platform.xop.common.util.encryption.Sign;

/**
 * 简单app service 验证实现类
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public class SampleAppValidateService implements AppValidateService {

    @Override
    public boolean available(String appKey) {
        return true;
    }
    
    @Override
    public boolean exists(String appKey) {
        return true;
    }

    @Override
    public boolean signVerify(String appKey, String signStr, String signature) {
        String publicKey = getAppPublicKey(appKey);
        boolean verify = Sign.verify(publicKey, signStr, signature);
        return verify;
    }

    @Override
    public String getAppPublicKey(String appKey) {
        String publicKey =
                "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKM8nJloFcufqoeUGYsMin4boFAxxaU4R6mJa6Bdr27vCsd2tmHL6PLcTyX/rnSbRIzHElK7xlGlSkXPIYRlsZsCAwEAAQ==";
        return publicKey;
    }

    @Override
    public String getAppPrivateKey(String appKey) {
        String privateKey =
                "MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEAozycmWgVy5+qh5QZiwyKfhugUDHFpThHqYlroF2vbu8Kx3a2Ycvo8txPJf+udJtEjMcSUrvGUaVKRc8hhGWxmwIDAQABAkEAik6IwNIX5XdbHZS2L8JodYEiy8/gDqUo0tTEhBPc2LgB6kwRsJYHVYCNKjMJ0dAO+7U/H8N86jHSKUtCY1muQQIhAPDo7NsQnGRXNGBtUd8ePaZPdqocRhEDspvqwBcXCoIdAiEArXYrwGiR/nzcG9DV/jqyAErLI2d23ewtyIcbrKRUNRcCIQDGhNqxhpWpZecGUu91ceERjEfX1Ca03JwxOZgDLvx8zQIhAIooArTfFNjizdUBwk8YpMmCyMTMFWqwP87lfW9F+yPFAiEAlSZ0tWzW+ZrCKBsaQzIh8HkLEz9CzN4INFj9Frf/oKw=";
        return privateKey;
    }
}
