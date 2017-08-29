package com.mqoo.xop.starter.token;

import org.apache.commons.lang3.StringUtils;

import com.mqoo.platform.xop.common.web.WebRequestContextHolder;
import com.mqoo.xop.starter.token.repo.AbstractTokenRepository;
import com.mqoo.xop.starter.token.session.TokenSession;
import com.mqoo.xop.starter.token.session.TokenSessionProxy;

/**
 * token 工具类
 * 
 * @author mingqi.wang
 * @since 2017/8/18
 *
 */
public class TokenUtil {
    public static AbstractTokenRepository tokenRepository;

    /**
     * 创建新TokenSession
     * 
     * @return tokenSession
     */
    public static TokenSession create() {
        TokenSession tokenSession = tokenRepository.create();
        TokenSession tokenSessionProxy = new TokenSessionProxy(tokenSession, tokenRepository);
        return tokenSessionProxy;
    }


    /**
     * 删除token
     */
    public static void remove() {
        String token = getToken();
        if (!StringUtils.isBlank(token)) {
            tokenRepository.remove(token);
        }
    }

    /**
     * 获取token session
     */
    public static TokenSession getTokenSession() {
        String token = getToken();
        if (StringUtils.isBlank(token)) {
            return null;
        }
        TokenSession tokenSession = tokenRepository.get(token);
        if (tokenSession == null) {
            return null;
        }
        TokenSession tokenSessionProxy = new TokenSessionProxy(tokenSession, tokenRepository);
        return tokenSessionProxy;
    }

    /**
     * 获取token
     * 
     * @return token
     */
    private static String getToken() {
        String token = WebRequestContextHolder.getToken();
        return token;
    }


    //
    public static AbstractTokenRepository getTokenRepository() {
        return tokenRepository;
    }

    public static void setTokenRepository(AbstractTokenRepository tokenRepository) {
        if (TokenUtil.tokenRepository == null) {
            TokenUtil.tokenRepository = tokenRepository;
        }
    }
}
