package com.mqoo.xop.starter.wechat.auth.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mqoo.platform.xop.common.util.HttpUtil;
import com.mqoo.platform.xop.common.web.WebRequestContextHolder;
import com.mqoo.platform.xop.common.web.api.response.BaseStatusSupportResponse;
import com.mqoo.xop.starter.wechat.auth.AuthRedirector;
import com.mqoo.xop.starter.wechat.auth.config.WechatAuthProperties;
import com.mqoo.xop.starter.wechat.utils.JsonUtils;

/**
 * Wechat auth Filter
 * <p>
 * 微信访问认证过滤器
 * 
 * @author mingqi.wang
 */
public class WechatAuthFilter extends OncePerRequestFilter {
    private WechatAuthProperties wechatAuthProperties;
    
    public WechatAuthFilter(WechatAuthProperties wechatAuthProperties) {
        this.wechatAuthProperties = wechatAuthProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                    FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse responseToUse = response;
        //
        responseToUse.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
        responseToUse.setHeader("Pragma", "no-cache"); // HTTP 1.0
        responseToUse.setDateHeader("Expires", 0); // prevents caching at the proxy server

        try {
            String requestURI = request.getRequestURI();
            String clientIp = HttpUtil.getIpAddr(request);
            WebRequestContextHolder.setClientIp(clientIp);
            if (!urlInPattern(requestURI)) {
                filterChain.doFilter(request, responseToUse);
            } else {
                String userId = SessionUtil.getUserId(request);
                if (userId == null) {
                    String referer = "";
                    if (request.getMethod().equalsIgnoreCase("get")) {
                        referer = wechatAuthProperties.getHost() + request.getRequestURI();
                        String queryString = request.getQueryString();
                        if (queryString != null) {
                            referer = referer + "?" + queryString;
                        }
                    }
                    String dispatcherUrl = AuthRedirector.REDIRECT_URL;
                    if (StringUtils.isNotBlank(referer)) {
                        dispatcherUrl = dispatcherUrl + "?"+AuthRedirector.REDIRECT_REFERER+"="
                                        + AuthRedirector.encodeRedirectUrl(referer);
                    }
                    if (HttpUtil.isAjaxRequest(request)) {
                        BaseStatusSupportResponse<String> rsp =new BaseStatusSupportResponse<>();
                        rsp.setStatus(HttpStatus.UNAUTHORIZED.value());
                        rsp.setData("USER_NO_LOGIN");
                        String rspJson = JsonUtils.toJson(rsp);
                        responseToUse.getWriter().write(rspJson);
                    } else {
                        request.getRequestDispatcher(dispatcherUrl).forward(request, response);
                    }

                } else {
                    WebRequestContextHolder.setUserId(userId);
                    filterChain.doFilter(request, responseToUse);
                }
            }
        } finally {
            WebRequestContextHolder.remove();
        }
    }

    
    private boolean urlInPattern(String requestUri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> ignoreUrls = wechatAuthProperties.getAuthPattern();
        for (String ignoreUrl : ignoreUrls) {
            if (antPathMatcher.match(ignoreUrl, requestUri)) {
                return true;
            }
        }
        return false;
    }

}
