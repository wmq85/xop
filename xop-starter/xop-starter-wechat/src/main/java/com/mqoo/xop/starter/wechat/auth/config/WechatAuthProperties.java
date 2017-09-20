package com.mqoo.xop.starter.wechat.auth.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * wechat auth properties
 *
 * @author mingqi.wang
 */
@ConfigurationProperties(prefix = "xop.wechat.auth", ignoreUnknownFields=true)
public class WechatAuthProperties {
    String host;
    String redirectPrefix;
    String authorizeUrl;
    String homeUrl;
    String errorUrl;
    List<String> authPattern=new ArrayList<String>();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRedirectPrefix() {
        return redirectPrefix;
    }

    public void setRedirectPrefix(String redirectPrefix) {
        this.redirectPrefix = redirectPrefix;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public List<String> getAuthPattern() {
        return authPattern;
    }

    public void setAuthPattern(List<String> authPattern) {
        this.authPattern = authPattern;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
