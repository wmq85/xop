package com.mqoo.xop.starter.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.mqoo.xop.starter.security.filter.SecurityFilter;
import com.mqoo.xop.starter.security.service.AppValidateService;
import com.mqoo.xop.starter.security.service.SampleAppValidateService;

/**
 * Security 配置
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Configuration
@ConditionalOnClass(SecurityProperties.class)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration {

    /**
     * Security filter 配置
     * 
     * @see SecurityFilter
     * @return FilterRegistrationBean
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE + 1)
    public FilterRegistrationBean securityFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SecurityFilter securityFilter = new SecurityFilter();
        registrationBean.setFilter(securityFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    /**
     * sample app validate service 配置
     * <p>
     * 默认 app validate service
     * 
     * @return AppValidateService
     */
    @Bean
    @ConditionalOnMissingBean(AppValidateService.class)
    public AppValidateService sampleAppValidateService() {
        AppValidateService sampleAppValidateService = new SampleAppValidateService();
        return sampleAppValidateService;
    }
}
