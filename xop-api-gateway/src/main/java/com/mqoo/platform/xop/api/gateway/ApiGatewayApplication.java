package com.mqoo.platform.xop.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.mqoo.platform.xop.api.gateway.filter.SecurityFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@RestController
public class ApiGatewayApplication {

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
