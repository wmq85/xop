package com.mqoo.xop.starter.wechat.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mqoo.xop.starter.wechat.auth.AuthRedirector;
import com.mqoo.xop.starter.wechat.auth.WechatSessionSupport;
import com.mqoo.xop.starter.wechat.auth.config.WechatAuthProperties;
import com.mqoo.xop.starter.wechat.auth.web.WechatAuthFilter;

/**
 * wechat auth configuration
 *
 * @author mingqi.wang
 */
@Configuration
@EnableConfigurationProperties(WechatAuthProperties.class)
public class WechatAuthConfiguration {
    @Autowired
    private WechatMpProperties wechatMpProperties;
    @Autowired
    private WechatAuthProperties wechatAuthProperties;

    @Bean
    public AuthRedirector authRedirector(){
        AuthRedirector authRedirector=new AuthRedirector();
        authRedirector.setWechatAuthProperties(wechatAuthProperties);
        authRedirector.setWechatMpProperties(wechatMpProperties);
        return authRedirector;
    }
    
    @Bean
    WechatSessionSupport wechatLoginSupport(){
        WechatSessionSupport wechatLoginSupport=createWechatLoginSupport();
        return wechatLoginSupport;
    }
    
    protected WechatSessionSupport createWechatLoginSupport(){
        WechatSessionSupport wechatLoginSupport=new WechatSessionSupport();
        return wechatLoginSupport;
    }
    
    /**
     * 微信用户认证filter
     * @return
     */
    @Bean
    public FilterRegistrationBean wechatAuthFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(wechatAuthFilter());
        registration.addUrlPatterns("/*");
        registration.setName("wechatAuthFilter");
        return registration;
    }

    @Bean(name = "wechatAuthFilter")
    public Filter wechatAuthFilter() {
        WechatAuthFilter wechatAuthFilter = new WechatAuthFilter(wechatAuthProperties);
        return wechatAuthFilter;
    }
    
}
