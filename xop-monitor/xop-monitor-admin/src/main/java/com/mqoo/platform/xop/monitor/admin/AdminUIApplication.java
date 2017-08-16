package com.mqoo.platform.xop.monitor.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;

@EnableAdminServer  
// 自动发现ZK指定节点下的应用进行监控  
@EnableDiscoveryClient  
@SpringBootApplication  
public class AdminUIApplication {  
    public static void main(String[] args) {  
        SpringApplication.run(AdminUIApplication.class, args);  
    }  
  
}