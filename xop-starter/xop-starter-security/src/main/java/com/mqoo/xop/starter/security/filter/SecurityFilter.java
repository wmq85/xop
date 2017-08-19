package com.mqoo.xop.starter.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mqoo.platform.xop.common.web.BufferedRequestWrapper;
import com.mqoo.xop.starter.security.SecurityValidateContext;
import com.mqoo.xop.starter.security.validator.SecurityValidatorHandler;
import com.mqoo.xop.starter.spring.util.SpringContextUtil;

/**
 * 安全验证过滤器
 * <p>
 * 此过滤器为安全校验的入口，通过调用{@link SecurityValidatorHandler#handle}方法完成校验逻辑
 * 
 * @author mingqi.wang
 * @see SecurityValidatorHandler#handle
 */
public class SecurityFilter extends OncePerRequestFilter {
    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        LOG.debug("SecurityFilter start.");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
        SecurityValidateContext securityValidateContext =
                new SecurityValidateContext(bufferedRequest);
        SecurityValidatorHandler securityValidatorHandler =
                SpringContextUtil.getBean(SecurityValidatorHandler.class);
        securityValidatorHandler.handle(securityValidateContext);
        //
        filterChain.doFilter(bufferedRequest, response);
    }

    // public static void main(String[] args) {
    // /*
    // * String[] keyPair=RSA.genRsa512PublicPrivateKeys();
    // * System.out.println(keyPair[0]); System.out.println(keyPair[1]);
    // */
    //
    // String privateKey =
    // "MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEAozycmWgVy5+qh5QZiwyKfhugUDHFpThHqYlroF2vbu8Kx3a2Ycvo8txPJf+udJtEjMcSUrvGUaVKRc8hhGWxmwIDAQABAkEAik6IwNIX5XdbHZS2L8JodYEiy8/gDqUo0tTEhBPc2LgB6kwRsJYHVYCNKjMJ0dAO+7U/H8N86jHSKUtCY1muQQIhAPDo7NsQnGRXNGBtUd8ePaZPdqocRhEDspvqwBcXCoIdAiEArXYrwGiR/nzcG9DV/jqyAErLI2d23ewtyIcbrKRUNRcCIQDGhNqxhpWpZecGUu91ceERjEfX1Ca03JwxOZgDLvx8zQIhAIooArTfFNjizdUBwk8YpMmCyMTMFWqwP87lfW9F+yPFAiEAlSZ0tWzW+ZrCKBsaQzIh8HkLEz9CzN4INFj9Frf/oKw=";
    // String body = "{\"countryname\":\"中国\",\"countrycode\":\"ZG\"}";
    // String time = "1498211604319";
    // String requestUri = "/countries";
    // String signStr = body + time + requestUri;
    // String sign = Sign.signature(privateKey, signStr);
    // System.out.println("sign:\n" + sign);
    //
    // String publicKey =
    // "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKM8nJloFcufqoeUGYsMin4boFAxxaU4R6mJa6Bdr27vCsd2tmHL6PLcTyX/rnSbRIzHElK7xlGlSkXPIYRlsZsCAwEAAQ==";
    // boolean verify = Sign.verify(publicKey, signStr, sign);
    // System.out.println("verify:\n" + verify);
    // }
}
