package com.mqoo.xop.starter.wechat.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import com.mqoo.xop.starter.wechat.auth.config.WechatAuthProperties;
import com.mqoo.xop.starter.wechat.config.WechatMpProperties;

/**
 * 赏吧微信配置
 * 
 * @author miles.wang
 *
 */
public class AuthRedirector {
    public final static String REDIRECT_URL_PARAM = "redirectUrl";
    public final static String REDIRECT_URL = "/authRedirect";
    public final static String REDIRECT_REFERER = "referer";
    
    private WechatAuthProperties wechatAuthProperties;
    private WechatMpProperties wechatMpProperties;

    /**
     * 生成微信授权(包含重定向)URL
     * 
     * @param mpAppId
     * @param refer
     * @return
     */
    public String authToRedirectUrl(String refer) {
        String mpAppId=wechatMpProperties.getAppId();
        String redirectPrefix=wechatAuthProperties.getRedirectPrefix();
        String authorizeUrl=wechatAuthProperties.getAuthorizeUrl();
        String url = redirectPrefix;
        if (StringUtils.isNotBlank(refer)) {
            String encodeStr = refer.startsWith("http") ? encodeRedirectUrl(refer) : refer;
            if (StringUtils.contains(redirectPrefix, "?")) {
                url = url + "&"+REDIRECT_URL_PARAM+"=" + encodeStr;
            } else {
                url = url + "?"+REDIRECT_URL_PARAM+"=" + encodeStr;
            }
        }
        String authRedirectUrl =
                        String.format(authorizeUrl, mpAppId, java.net.URLEncoder.encode(url));
        return authRedirectUrl;
    }

    public static String encodeRedirectUrl(String redirectUrl) {
        String s1 = Base64Utils.encodeToString(redirectUrl.getBytes());
        return s1;
    }

    public static String decodeRedirectUrl(String str) {
        String s1 = new String(Base64Utils.decodeFromString(str));
        return s1;
    }

    public void setWechatAuthProperties(WechatAuthProperties wechatAuthProperties) {
        this.wechatAuthProperties = wechatAuthProperties;
    }

    public void setWechatMpProperties(WechatMpProperties wechatMpProperties) {
        this.wechatMpProperties = wechatMpProperties;
    }
}
