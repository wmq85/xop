package com.mqoo.platform.xop.support.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * security 配置类
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
@ConfigurationProperties("platform.security")
public class SecurityProperties {
    /**
     * 忽略url
     */
    private List<String> ignoreSecurityUrls=new ArrayList<>();
    /**
     * 全局忽略url
     */
    private List<String> globalIgnoreSecurityUrls=new ArrayList<>();
    /**
     * 是否启用时间戳验证器
     */
    private boolean enableRequestTimestampValidator=true;
    /**
     * 是否启用app验证器
     */
    private boolean enableAppStatusValidator=true;
    /**
     * 是否启用签名验证器
     */
    private boolean enableSignatureValidator=true;

	public boolean isEnableRequestTimestampValidator() {
		return enableRequestTimestampValidator;
	}

	public void setEnableRequestTimestampValidator(boolean enableRequestTimestampValidator) {
		this.enableRequestTimestampValidator = enableRequestTimestampValidator;
	}

	public boolean isEnableAppStatusValidator() {
		return enableAppStatusValidator;
	}

	public void setEnableAppStatusValidator(boolean enableAppStatusValidator) {
		this.enableAppStatusValidator = enableAppStatusValidator;
	}

    public List<String> getIgnoreSecurityUrls() {
        return ignoreSecurityUrls;
    }

    public void setIgnoreSecurityUrls(List<String> ignoreSecurityUrls) {
        this.ignoreSecurityUrls = ignoreSecurityUrls;
    }

    public boolean isEnableSignatureValidator() {
        return enableSignatureValidator;
    }

    public void setEnableSignatureValidator(boolean enableSignatureValidator) {
        this.enableSignatureValidator = enableSignatureValidator;
    }

    public List<String> getGlobalIgnoreSecurityUrls() {
        return globalIgnoreSecurityUrls;
    }

    public void setGlobalIgnoreSecurityUrls(List<String> globalIgnoreSecurityUrls) {
        this.globalIgnoreSecurityUrls = globalIgnoreSecurityUrls;
    }
}
