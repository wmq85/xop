package com.mqoo.platform.xop.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * config server
 *
 */
@RestController
@Configuration
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServerApplication {
	
	@RequestMapping("/")
	public String home() {
	  return "config server.";
	}
	
    public static void main(String... args) {
        SpringApplication.run(ConfigServerApplication.class, args).getEnvironment();
    }

}
