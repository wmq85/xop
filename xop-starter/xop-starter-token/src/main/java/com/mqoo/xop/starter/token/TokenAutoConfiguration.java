package com.mqoo.xop.starter.token;

import javax.servlet.Filter;

import org.aspectj.lang.Aspects;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.mqoo.xop.starter.token.filter.TokenFilter;
import com.mqoo.xop.starter.token.repo.AbstractTokenRepository;
import com.mqoo.xop.starter.token.repo.RedisTokenRepository;

/**
 * token 配置
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Configuration
@EnableConfigurationProperties({TokenProperties.class})
@ConditionalOnProperty(havingValue = "true", name = "xop.token.enabled")
public class TokenAutoConfiguration {
    @Autowired
    private TokenProperties tokenProperties;
    @Value("${spring.application.name: DEFAULT}")
    private String mapCacheKey;
    
    @Bean
    public TokenCheckAspect tokenCheckAspect() {
        TokenCheckAspect aspect = Aspects.aspectOf(TokenCheckAspect.class);
        // ... inject dependencies here if not using @Autowired
        return aspect;
    }

    /**
     * 配置 token util
     */
    @Bean
    public TokenUtil tokenUtil(AbstractTokenRepository tokenRepository) {
        TokenUtil.setTokenRepository(tokenRepository);
        return new TokenUtil();
    }

    /**
     * 配置 tokenRepository
     */
    @Bean
    public AbstractTokenRepository tokenRepository(RedissonClient redissonClient) {
        RedisTokenRepository tokenRepository =
                new RedisTokenRepository(redissonClient, mapCacheKey);
        Long ttl = tokenProperties.getExpiredInSeconds();
        tokenRepository.setTokenTimeoutSeconds(ttl);
        return tokenRepository;
    }

    /**
     * token filter config
     * 
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE - 1)
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(tokenFilter());
        registration.addUrlPatterns("/*");
        registration.setName("tokenFilter");
        return registration;
    }

    @Bean(name = "tokenFilter")
    public Filter tokenFilter() {
        return new TokenFilter();
    }
}
