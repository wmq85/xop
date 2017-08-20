package com.mqoo.xop.starter.cache.redis;

import java.io.IOException;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.session.RedissonSessionRepository;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mqoo.platform.xop.common.util.GsonUtil;

/**
 * redisson 配置类
 * <p>
 * 
 * @author mingqi.wang
 * @since 2017/08/09
 */
@Configuration
@EnableConfigurationProperties({RedissonProperties.class, SpringCacheProperties.class})
public class RedissonAutoConfiguration {
    @Autowired
    SpringCacheProperties springCacheProperties;

    /**
     * 配置 RedissonClient
     * 
     * @param redissonProperties redissonProperties
     * @return RedissonClient
     * @throws IOException
     */
    @Bean
    public RedissonClient redisson(RedissonProperties redissonProperties) throws IOException {
        String redissonConfigJson = GsonUtil.toJson(redissonProperties);
        return Redisson.create(Config.fromJSON(redissonConfigJson));
    }


    /**
     * 配置 redisson spring CacheManager
     * 
     * @param redissonClient RedissonClient
     * @return CacheManager
     * @throws IOException
     */
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) throws IOException {
        Map<String, CacheConfig> cacheNames = springCacheProperties.getCacheNames();
        return new RedissonSpringCacheManager(redissonClient, cacheNames);
    }
}


/**
 * 配置redisson spring session
 * <p>
 * 只有在{@code ifsp.redisson.enableHttpSession} 为true时才加载此配置
 */
@EnableRedissonHttpSession
@ConditionalOnProperty(havingValue = "true", name = "xop.cache.enableHttpSession")
class HttpSessionConfig {


    @Value("${server.session.timeout:1800}")
    private Integer sessionTimeout;

    /**
     * RedissonSessionRepository 配置，使sprign session timeout时间与sessionTimeout保持一致
     */
    @Bean
    @ConditionalOnProperty(name = "server.session.timeout")
    public RedissonSessionRepository sessionRepository(RedissonClient redissonClient,
            ApplicationEventPublisher eventPublisher) {
        RedissonSessionRepository repository =
                new RedissonSessionRepository(redissonClient, eventPublisher);
        repository.setDefaultMaxInactiveInterval(sessionTimeout);
        return repository;
    }
}
