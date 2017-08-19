package com.mqoo.xop.starter.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.FixedContentNegotiationStrategy;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mqoo.xop.starter.exception.filter.ExceptionHandlerFilter;
import com.mqoo.xop.starter.exception.handler.DefaultRestErrorResolver;
import com.mqoo.xop.starter.exception.handler.RestErrorResolver;
import com.mqoo.xop.starter.exception.handler.RestExceptionHandler;

/**
 * WebMvcConfiguration
 * <p>
 * 配置异常处理{@link RestExceptionHandler}、{@link ExceptionHandlerFilter}<br>
 * 配置api多版本兼容{@link CustomRequestMappingHandlerMapping}<br>
 * 
 * @see ExceptionProperties
 * @see RestExceptionHandler
 * @see ExceptionHandlerFilter
 * @see CustomRequestMappingHandlerMapping
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Configuration
@ConditionalOnClass(ExceptionProperties.class)
@EnableConfigurationProperties(ExceptionProperties.class)
public class ExceptionAutoConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private ExceptionProperties exceptionProperties;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        super.addInterceptors(registry);
    }

    @Override
    protected void configureHandlerExceptionResolvers(
            List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(restExceptionHandler());
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Bean
    public HandlerExceptionResolver restExceptionHandler() {
        RestExceptionHandler restExceptionHandler = new RestExceptionHandler();
        restExceptionHandler.setMessageConverters(
                new HttpMessageConverter[] {defaultJacksonHttpMessageConverter()});
        restExceptionHandler.setErrorResolver(defaultRestErrorResolver());
        restExceptionHandler.setOrder(exceptionProperties.getExceptionHandlerOrder());
        return restExceptionHandler;
    }

    @Bean
    public RestErrorResolver defaultRestErrorResolver() {
        DefaultRestErrorResolver defaultRestErrorResolver = new DefaultRestErrorResolver();
        // defaultRestErrorResolver.setLocaleResolver(localeResolver());
        defaultRestErrorResolver.setDefaultEmptyCodeToStatus(false);

        defaultRestErrorResolver
                .setDefaultMoreInfoUrl(this.exceptionProperties.getDefaultMoreInfoUrl());
        Map<String, String> exceptionMappingDefinitions = Maps.newLinkedHashMap();

        // load definitions from config file
        if (null != this.exceptionProperties.getDefinitions()) {
            exceptionMappingDefinitions.putAll(this.exceptionProperties.getDefinitions());
        }

        defaultRestErrorResolver.setExceptionMappingDefinitions(exceptionMappingDefinitions);
        return defaultRestErrorResolver;
    }

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
    }

    @Bean(name = "jacksonHttpMessageConverter")
    public HttpMessageConverter defaultJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setPrettyPrint(true);
        return converter;
    }

    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /*
     * @Bean(name = "localeResolver") public LocaleResolver localeResolver() { return new
     * ComplexLocaleResolver(); }
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver() {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setOrder(1);
        resolver.setContentNegotiationManager(contentNegotiationManager());
        resolver.setDefaultViews(Lists.newArrayList(mappingJackson2JsonView()));
        return resolver;
    }

    @Bean
    public ContentNegotiationManager contentNegotiationManager() {
        return new ContentNegotiationManager(contentNegotiationStrategy());
    }

    @Bean
    public ContentNegotiationStrategy contentNegotiationStrategy() {
        return new FixedContentNegotiationStrategy(MediaType.APPLICATION_JSON_UTF8);
    }

    @Bean
    public View mappingJackson2JsonView() {
        return new MappingJackson2JsonView();
    }

    /**
     * 异常拦截 filter
     * 
     * @param handlerExceptionResolver
     * @return FilterRegistrationBean
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public FilterRegistrationBean exceptionHandlerFilterRegistrationBean(
            HandlerExceptionResolver handlerExceptionResolver) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ExceptionHandlerFilter(handlerExceptionResolver));
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
