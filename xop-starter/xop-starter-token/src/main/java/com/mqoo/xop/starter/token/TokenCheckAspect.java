package com.mqoo.xop.starter.token;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.google.common.collect.Lists;
import com.mqoo.platform.xop.common.util.HttpUtil;
import com.mqoo.xop.starter.token.annotation.AuthorizationToken;
import com.mqoo.xop.starter.token.session.TokenSession;

/**
 * 是否需要验证token 检测切面
 * <p>
 * 根据controller方法{@link NeekToken}注解，判断该方法是否需要token
 * 
 * @author mingqi.wang
 * @since 2018/8/18
 */
//@Component
//@Aspect
public class TokenCheckAspect {
    Logger LOG = LoggerFactory.getLogger(getClass());
    private TokenProperties tokenProperties;
    

    public void setTokenProperties(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void tokenCheck() {}

    @Before("tokenCheck()")
    public void tokenNeedCheck(JoinPoint joinPoint) {
        HttpServletRequest request = HttpUtil.getCurrentHttpRequest();
        String requestUri = request.getRequestURI();
        if (urlInGloableIgnore(requestUri) || urlInIgnore(requestUri)) {
            LOG.debug("uri:{} in ignore url list", requestUri);
            return;
        }
        //
//        String targetName=joinPoint.getTarget().getClass().getName();
//        if(inScanPackages(targetName)){
//            LOG.debug("joinPoint target:{} no in scan packages", targetName);
//            return;
//        }
        //
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        AuthorizationToken annoClass = method.getAnnotation(AuthorizationToken.class);
        if (annoClass == null || annoClass.value()) {
            TokenSession tokenSession = TokenUtil.getTokenSession();
            if (tokenSession == null) {
                throw new TokenAuthorizationException();
            }
        }
    }

    /**
     * 是否在扫描包列表中
     * @param targetName
     * @return
     */
    private boolean inScanPackages(String targetName){
        List<String> scanPageages = tokenProperties.getScanPackages();
        for (String scanPageage : scanPageages) {
            if (StringUtils.startsWith(scanPageage, targetName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断uri是否在忽略列表中
     * 
     * @param requestUri
     * @return
     */
    private boolean urlInIgnore(String requestUri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> ignoreUrls = tokenProperties.getIgnoreUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (antPathMatcher.match(ignoreUrl, requestUri)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断uri是否在忽略列表中
     * 
     * @param requestUri
     * @return
     */
    private boolean urlInGloableIgnore(String requestUri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String>  gloableIgnoreUrls=tokenProperties.getGloableIgnoreUrls();
        //default add
        gloableIgnoreUrls.add("/error");
        gloableIgnoreUrls.add("/static/**");
        //
        for (String ignoreUrl : gloableIgnoreUrls) {
            if (antPathMatcher.match(ignoreUrl, requestUri)) {
                return true;
            }
        }
        return false;
    }
}
