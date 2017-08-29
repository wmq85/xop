package com.mqoo.xop.starter.token;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * token 配置属性类
 * 
 * @author mingqi.wang
 * @since 2017/8/18
 *
 */
@ConfigurationProperties(prefix = "xop.token", ignoreUnknownFields = true)
public class TokenProperties {
    private final Long DEFAULT_TOKEN_EXPIRED_SECONDS = 60 * 60 * 24L;
    private boolean enabled = false;
    private Long expiredInSeconds = DEFAULT_TOKEN_EXPIRED_SECONDS;
    private List<String> ignoreUrls = new ArrayList<>();
    private List<String> gloableIgnoreUrls = new ArrayList<>();
    private List<String> scanPackages = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getExpiredInSeconds() {
        return expiredInSeconds;
    }

    public void setExpiredInSeconds(Long expiredInSeconds) {
        this.expiredInSeconds = expiredInSeconds;
    }

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    public List<String> getGloableIgnoreUrls() {
        return gloableIgnoreUrls;
    }

    public void setGloableIgnoreUrls(List<String> gloableIgnoreUrls) {
        this.gloableIgnoreUrls = gloableIgnoreUrls;
    }

    public List<String> getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }
}
