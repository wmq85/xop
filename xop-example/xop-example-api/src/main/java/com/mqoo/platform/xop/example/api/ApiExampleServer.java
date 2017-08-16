package com.mqoo.platform.xop.example.api;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ifsp api server 
 *
 */
@Controller
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.mqoo")
@MapperScan(basePackages={"com.mqoo.platform.xop.example.api.mapper"})
public class ApiExampleServer 
{
	@RequestMapping("/")
    @ResponseBody
    String home(HttpServletRequest request) {
        return "api server.";
    }
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(ApiExampleServer.class, args);
    }
}
