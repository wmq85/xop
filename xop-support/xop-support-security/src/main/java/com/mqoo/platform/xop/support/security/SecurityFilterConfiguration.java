package com.mqoo.platform.xop.support.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.mqoo.platform.xop.support.security.filter.SecurityFilter;
import com.mqoo.platform.xop.support.security.service.AppValidateService;
import com.mqoo.platform.xop.support.security.service.SampleAppValidateService;

//@Configuration
//@EnableConfigurationProperties(SecurityProperties.class)
@Deprecated
public class SecurityFilterConfiguration {
	/*@Bean  
    public FilterRegistrationBean filterRegistrationBean(){  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        SignatureVerifyFilter signatureVerifyFilter = new SignatureVerifyFilter();  
        registrationBean.setFilter(signatureVerifyFilter);  
        List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add("/*");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }  */
	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE+1)
	public FilterRegistrationBean securityFilterRegistrationBean(){  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        SecurityFilter securityFilter = new SecurityFilter();  
        registrationBean.setFilter(securityFilter);  
        List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add("/*");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }
	
	@Bean
	@ConditionalOnMissingBean(AppValidateService.class)
	public AppValidateService sampleAppValidateService(){
	    AppValidateService sampleAppValidateService=new SampleAppValidateService();
	    return sampleAppValidateService;
	}
	
}