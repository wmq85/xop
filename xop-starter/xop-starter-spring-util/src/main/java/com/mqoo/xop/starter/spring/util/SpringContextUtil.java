package com.mqoo.xop.starter.spring.util;

import org.springframework.context.ApplicationContext;

/**
 * spring context 工具类
 * 
 * @author mingqi.wang
 *
 */
public class SpringContextUtil /* implements ApplicationContextAware */ {
    private static ApplicationContext applicationContext;

    /*
     * @Override public void setApplicationContext(ApplicationContext applicationContext) {
     * SpringContextUtil.applicationContext = applicationContext; }
     */

    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }
}
