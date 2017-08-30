package com.mqoo.xop.starter.token.repo;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import com.mqoo.xop.starter.token.TokenAuthorizationException;
import com.mqoo.xop.starter.token.session.SampleTokenSession;
import com.mqoo.xop.starter.token.session.TokenSession;

/**
 * redis token repository
 * 
 * @author mingqi.wang
 * @since 2018/8/18
 */
public class RedisTokenRepository extends AbstractTokenRepository {
    private RedissonClient redissonClient;
    private String mapCacheKey;

    public RedisTokenRepository(RedissonClient redissonClient, String mapCacheKey) {
        this.redissonClient = redissonClient;
        this.mapCacheKey = mapCacheKey;
    }

    @Override
    public TokenSession create() {
        long ttl = getTokenTimeoutSeconds();
        return create(ttl);
    }

    private TokenSession create(long ttl) {
        String token = newToken();
        TokenSession tokenSession = new SampleTokenSession(token, ttl);

        RMapCache<String, TokenSession> mapCache = getMapCache();
        mapCache.put(token, tokenSession, ttl, TimeUnit.SECONDS);
        return tokenSession;
    }

    @Override
    public void merge(TokenSession tokenSession) {
        RMapCache<String, TokenSession> mapCache = getMapCache();
        String token = tokenSession.getToken();
        TokenSession val = mapCache.get(token);
        if (val == null) {
            throw new TokenAuthorizationException();
        }
        refresh(tokenSession);
    }

    @Override
    public TokenSession get(String token) {
        RMapCache<String, TokenSession> mapCache = getMapCache();
        TokenSession tokenSession = mapCache.get(token);
        refresh(tokenSession);
        return tokenSession;
    }

    @Override
    public void remove(String token) {
        RMapCache<String, TokenSession> mapCache = getMapCache();
        mapCache.remove(token);
    }

    @Override
    public TokenSession touch(String token) {
        RMapCache<String, TokenSession> mapCache = getMapCache();
        TokenSession tokenSession = mapCache.get(token);
        refresh(tokenSession);
        return tokenSession;
    }

    /**
     * 刷新token 过期时间
     * 
     * @param token
     * @param mapValue
     */
    private void refresh(TokenSession tokenSession) {
        RMapCache<String, TokenSession> mapCache = getMapCache();
        if (tokenSession != null) {
            tokenSession.refreshExpiredAt();
            String token = tokenSession.getToken();
            long ttl = tokenSession.getTtl();
            mapCache.put(token, tokenSession, ttl, TimeUnit.SECONDS);
        }
    }

    private RMapCache<String, TokenSession> getMapCache() {
        String cacheName = "TOKEN_CACHE_" + mapCacheKey;
        RMapCache<String, TokenSession> mapCache =
                redissonClient.getMapCache(cacheName.toUpperCase());
        return mapCache;
    }

}

