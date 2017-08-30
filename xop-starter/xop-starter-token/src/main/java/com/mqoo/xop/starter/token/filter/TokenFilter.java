package com.mqoo.xop.starter.token.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mqoo.platform.xop.common.util.HttpUtil;
import com.mqoo.platform.xop.common.web.WebRequestContextHolder;
import com.mqoo.platform.xop.common.web.api.Constants;

/**
 * token filter，用来解析request中的token，并放入{@link WebRequestContextHolder}中
 * 
 * @author mingqi.wang
 * @since 2018/8/18
 *
 */
public class TokenFilter extends OncePerRequestFilter {
    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        LOG.debug("TokenFilter start.");
        HttpServletResponse responseToUse = response;
        try {
            String appKey = Constants.getAppKeyFromRequest(request);
            String token = Constants.getTokenFromRequest(request);
            // 设置ip
            String ip = HttpUtil.getIpAddr(request);
            WebRequestContextHolder.setClientIp(ip);
            WebRequestContextHolder.setAppKey(appKey);
            if (token != null) {
                WebRequestContextHolder.setToken(token);
            }
            filterChain.doFilter(request, responseToUse);
        } finally {
            WebRequestContextHolder.remove();
        }
    }
}
