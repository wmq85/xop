package com.mqoo.platform.xop.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import com.mqoo.platform.xop.api.gateway.filter.ErrorFilter;
import com.mqoo.platform.xop.api.gateway.filter.SamplePreFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@RestController
@ComponentScan(basePackages = "com.ifinance")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class ApiGatewayApplication {
    
    @Bean  
    public SamplePreFilter queryParamPreFilter(){  
        return new SamplePreFilter();  
    }  
    
    @Bean  
    public ErrorFilter errorFilter(){  
        return new ErrorFilter();  
    }
    
    
    
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}