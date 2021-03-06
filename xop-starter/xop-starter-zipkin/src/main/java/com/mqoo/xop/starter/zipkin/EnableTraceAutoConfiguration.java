package com.mqoo.xop.starter.zipkin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.mqoo.xop.starter.zipkin.annotation.EnableTraceAutoConfigurationProperties;
import com.mqoo.xop.starter.zipkin.context.TraceContext;
import com.mqoo.xop.starter.zipkin.trace.config.TraceConfig;

@Configuration
@ConditionalOnBean(annotation = EnableTraceAutoConfigurationProperties.class)
@AutoConfigureAfter(SpringBootConfiguration.class)
@EnableConfigurationProperties(TraceConfig.class)
@ImportResource(value={"service-context.xml"})
public class EnableTraceAutoConfiguration {
    
    @Autowired
    private TraceConfig traceConfig;

    @PostConstruct
    public void init() throws Exception {
        TraceContext.init(this.traceConfig);
    }

    /**
     * trace filter 配置
     * 
     * @return FilterRegistrationBean
     */
//    @Bean
//    public FilterRegistrationBean traceFilterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(traceFilter(traceConfig));
//        List<String> urlPatterns = new ArrayList<String>();
//        urlPatterns.add("/*");
//        registrationBean.setUrlPatterns(urlPatterns);
//        return registrationBean;
//    }
//
//    @Bean
//    TraceFilter traceFilter(TraceConfig traceConfig) {
//        TraceFilter traceFilter = new TraceFilter(traceConfig);
//        return traceFilter;
//    }
}
