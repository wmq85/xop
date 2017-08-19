package com.mqoo.xop.starter.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mqoo.xop.starter.security.filter.SecurityFilter;
import com.mqoo.xop.starter.security.service.AppValidateService;
import com.mqoo.xop.starter.security.service.SampleAppValidateService;
import com.mqoo.xop.starter.security.validator.SecurityValidator;
import com.mqoo.xop.starter.security.validator.SecurityValidatorHandler;
import com.mqoo.xop.starter.security.validator.impl.AppStatusValidator;
import com.mqoo.xop.starter.security.validator.impl.RequestSignatureValidator;
import com.mqoo.xop.starter.security.validator.impl.TimestampValidator;

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
    // @Bean
    // @Order(Ordered.LOWEST_PRECEDENCE + 1)
    // public FilterRegistrationBean securityFilterRegistrationBean() {
    // FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    // SecurityFilter securityFilter = new SecurityFilter();
    // registrationBean.setFilter(securityFilter);
    // List<String> urlPatterns = new ArrayList<String>();
    // urlPatterns.add("/*");
    // registrationBean.setUrlPatterns(urlPatterns);
    // return registrationBean;
    // }
    @Bean
    public SecurityValidatorHandler SecurityValidatorHandler(SecurityProperties securityProperties,
            SecurityValidator[] securityValidators) {
        SecurityValidatorHandler securityValidatorHandler =
                new SecurityValidatorHandler(securityValidators, securityProperties);
        return securityValidatorHandler;
    }

    @Bean
    public AppStatusValidator appStatusValidator(SecurityProperties securityProperties,
            AppValidateService appValidateService) {
        return new AppStatusValidator(appValidateService, securityProperties);
    }

    @Bean
    public RequestSignatureValidator requestSignatureValidator(
            SecurityProperties securityProperties, AppValidateService appValidateService) {
        return new RequestSignatureValidator(appValidateService, securityProperties);
    }

    @Bean
    public TimestampValidator timestampValidator(SecurityProperties securityProperties) {
        return new TimestampValidator(securityProperties);
    }

    /**
     * sample app validate service 配置
     * <p>
     * 默认 app validate service
     * 
     * @return AppValidateService
     */
    @Bean
    public AppValidateService sampleAppValidateService(SecurityProperties securityProperties) {
        AppValidateService sampleAppValidateService =
                new SampleAppValidateService(securityProperties);
        return sampleAppValidateService;
    }
}
