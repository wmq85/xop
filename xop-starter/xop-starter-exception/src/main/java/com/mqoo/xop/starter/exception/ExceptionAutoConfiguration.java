package com.mqoo.xop.starter.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

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
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Configuration
@ConditionalOnClass(ExceptionProperties.class)
@EnableConfigurationProperties(ExceptionProperties.class)
public class ExceptionAutoConfiguration {

    @Autowired
    private ExceptionProperties exceptionProperties;

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
    /*
     * @Bean public ViewResolver contentNegotiatingViewResolver() { ContentNegotiatingViewResolver
     * resolver = new ContentNegotiatingViewResolver(); resolver.setOrder(1);
     * resolver.setContentNegotiationManager(contentNegotiationManager());
     * resolver.setDefaultViews(Lists.newArrayList(mappingJackson2JsonView())); return resolver; }
     * 
     * @Bean public ContentNegotiationManager contentNegotiationManager() { return new
     * ContentNegotiationManager(contentNegotiationStrategy()); }
     * 
     * @Bean public ContentNegotiationStrategy contentNegotiationStrategy() { return new
     * FixedContentNegotiationStrategy(MediaType.APPLICATION_JSON_UTF8); }
     * 
     * @Bean public View mappingJackson2JsonView() { return new MappingJackson2JsonView(); }
     */

    /**
     * 配置ErrorAttributes, 处理未被ExceptionHandler捕获的异常
     * 
     * @param restExceptionHandler
     * @return
     */
    @Bean
    public ErrorAttributes errorAttributes(HandlerExceptionResolver restExceptionHandler) {

        return new DefaultErrorAttributes() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request,
                    HttpServletResponse response, Object handler, Exception ex) {
                return restExceptionHandler.resolveException(request, response,
                        restExceptionHandler, ex);
            }
        };
    }
}
