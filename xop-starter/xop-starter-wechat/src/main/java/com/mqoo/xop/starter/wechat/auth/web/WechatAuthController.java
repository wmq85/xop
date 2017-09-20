package com.mqoo.xop.starter.wechat.auth.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqoo.platform.xop.common.web.api.response.BaseStatusSupportResponse;
import com.mqoo.xop.starter.wechat.auth.AuthRedirector;
import com.mqoo.xop.starter.wechat.auth.WechatSessionSupport;
import com.mqoo.xop.starter.wechat.auth.config.WechatAuthProperties;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 微信授权认证controller
 * 
 * @author miles.wang
 *
 */
@Controller
public class WechatAuthController {
    final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    AuthRedirector authRedirector;
    @Autowired
    WxMpService wxMpService;
    @Autowired
    WechatSessionSupport wechatLoginSupport;
    @Autowired
    WechatAuthProperties wechatAuthProperties;

    /**
     * 重定向到微信授权页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(AuthRedirector.REDIRECT_AUTH_URL)
    String authRedirect(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getParameter("referer");
        String authRedirectUrl = authRedirector.authToRedirectUrl(referer);
        return "redirect:" + authRedirectUrl;
    }


    /**
     * 微信授权后的重定向页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/wechat/redirect", method = RequestMethod.GET)
    public String redirect(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String redirectUrl = request.getParameter("redirectUrl");
        // 判断code是否为空
        if (StringUtils.isBlank(code)) {
            return "forward:/wechat/authRedirect";
        }

        //
        WxMpOAuth2AccessToken oauth2AccessToken = null;
        try {
            oauth2AccessToken = wxMpService.oauth2getAccessToken(code);
            String openId = oauth2AccessToken.getOpenId();
            // 判断openid是否为空
            if (StringUtils.isBlank(openId)) {
                return "forward:/authRedirect";
            }

            // get user
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(oauth2AccessToken, "zh_CN");
            LOG.debug("WX mp user:{}", wxMpUser);
            // handle user
            String userId = wechatLoginSupport.createSession(wxMpUser);
            // 跳转到页面
            if (StringUtils.isNotBlank(redirectUrl)) {
                LOG.debug("redirect url base64:{}", redirectUrl);
                String decodeUrl = AuthRedirector.decodeRedirectUrl(redirectUrl);
                String url = replacePlaceHolder(decodeUrl, "USER_ID", userId);
                LOG.debug("url to redirect:{}, userId:{}", url, userId);
                return "redirect:" + url;
            }
        } catch (WxErrorException e) {
            LOG.error(e.getMessage(), e);
            WxError err = e.getError();
            if (err != null) {
                // 无法获取openid
                return "forward:/authRedirect";
            }
        }
        String home = wechatAuthProperties.getHomeUrl();
        return "redirect:" + home;
    }

    /**
     * 获取js sdk config
     * 
     * @param signUrl
     * @return
     * @throws WxErrorException
     */
    @RequestMapping("/wechat/jsconfig")
    @ResponseBody
    public BaseStatusSupportResponse<Map<String, String>> jsconfig(String signUrl)
            throws WxErrorException {
        // signature
        String url = StringUtils.isEmpty(signUrl) ? wechatAuthProperties.getHomeUrl().trim()
                : StringUtils.trim(signUrl);
        LOG.debug("Sign>>> url:{}", url);
        WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(url);
        String signature = jsapiSignature.getSignature();
        LOG.debug("SHA1: {} ", signature);
        Map<String, String> m = new HashMap<>();
        m.put("appId", wxMpService.getWxMpConfigStorage().getAppId());
        m.put("timestamp", String.valueOf(jsapiSignature.getTimestamp()));
        m.put("nonceStr", jsapiSignature.getNonceStr());
        m.put("signature", signature);
        return BaseStatusSupportResponse.buildSuccessResponse(m);
    }

    private String replacePlaceHolder(String str, String replaceParam, String replacement) {
        return str.replaceAll("\\{\\{" + replaceParam + "\\}\\}", replacement);
    }
}
