package com.mqoo.platform.xop.support.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mqoo.platform.xop.common.util.SpringContextUtil;

/**
 * spring 配置
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Configuration
public class SpringAutoConfiguration {
    
    /**
     * SpringContext工具类注册
     * 
     * @param context
     * @return SpringContextUtil
     */
	@Bean
	@ConditionalOnClass(SpringContextUtil.class)
	public SpringContextUtil getSpringContextUtil(ApplicationContext context){
		SpringContextUtil.setApplicationContext(context);
		return new SpringContextUtil();
	}
}
