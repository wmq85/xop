package com.mqoo.platform.xop.example.dubbo.provider;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * dubbo provider example server
 *
 */
@Controller
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.mqoo")
@MapperScan(basePackages = {"com.mqoo.platform.xop.example.dubbo.provider.mapper"})
@EnableDiscoveryClient
public class DubboProviderExampleServer {

    @RequestMapping("/")
    String home(HttpServletRequest request) {
        return "dubbo provider example server.";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DubboProviderExampleServer.class, args);
    }
}
