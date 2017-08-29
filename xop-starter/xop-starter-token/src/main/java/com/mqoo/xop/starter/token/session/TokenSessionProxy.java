package com.mqoo.xop.starter.token.session;

import java.io.Serializable;

import com.mqoo.xop.starter.token.repo.AbstractTokenRepository;

/**
 * token session 代理
 * 
 * @author mingqi.wang
 * @since 2017/8/18
 */
public class TokenSessionProxy implements TokenSession {
    /**
     * 
     */
    private static final long serialVersionUID = 2152081937908729736L;
    TokenSession tokenSession;
    AbstractTokenRepository tokenRepository;

    public TokenSessionProxy(TokenSession tokenSession, AbstractTokenRepository tokenRepository) {
        this.tokenSession = tokenSession;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String getToken() {
        return tokenSession.getToken();
    }

    @Override
    public <T extends Serializable> T getAttr(String key) {
        return tokenSession.getAttr(key);
    }

    @Override
    public void setAttr(String key, Serializable val) {
        tokenSession.setAttr(key, val);
        tokenRepository.merge(tokenSession);
    }

    @Override
    public long getTtl() {
        return tokenSession.getTtl();
    }

    @Override
    public long expiredAt() {
        return tokenSession.expiredAt();
    }

    @Override
    public void refreshExpiredAt() {
        tokenSession.refreshExpiredAt();
        tokenRepository.merge(tokenSession);
    }

    @Override
    public TokenSession getObject() {
        return tokenSession;
    }

}
