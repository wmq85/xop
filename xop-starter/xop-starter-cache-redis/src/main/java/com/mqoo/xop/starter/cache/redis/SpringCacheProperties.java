package com.mqoo.xop.starter.cache.redis;

import java.util.HashMap;
import java.util.Map;

import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson spring cache 配置
 * 
 * @author mingqi.wang
 *
 */
@ConfigurationProperties(prefix = "xop.cache", ignoreUnknownFields = true)
public class SpringCacheProperties {
    private Map<String, CacheConfig> cacheNames = new HashMap<>();

    public Map<String, CacheConfig> getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(Map<String, CacheConfig> cacheNames) {
        this.cacheNames = cacheNames;
    }
}
