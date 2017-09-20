package com.mqoo.xop.starter.wechat.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;

import com.mqoo.xop.starter.wechat.auth.web.WechatAuthController;
import com.mqoo.xop.starter.wechat.endpoint.WechatController;
import com.mqoo.xop.starter.wechat.endpoint.WxMenuController;

/**
 * wechat endpoint configuration
 *
 * @author mingqi.wang
 */
@Configuration
public class WechatEndpointConfiguration implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {}

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
            throws BeansException {
        BeanDefinition wechatControllerDefinition =
                new RootBeanDefinition(WechatController.class, Autowire.BY_TYPE.value(), true);
        BeanDefinition wxMenuControllerDefinition =
                new RootBeanDefinition(WxMenuController.class, Autowire.BY_TYPE.value(), true);
        BeanDefinition wxAuthControllerDefinition =
                new RootBeanDefinition(WechatAuthController.class, Autowire.BY_TYPE.value(), true);
        registry.registerBeanDefinition("wechatController", wechatControllerDefinition);
        registry.registerBeanDefinition("wxMenuController", wxMenuControllerDefinition);
        registry.registerBeanDefinition("wxAuthController", wxAuthControllerDefinition);
    }

}
