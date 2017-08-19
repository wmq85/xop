package com.mqoo.xop.starter.exception;

import java.util.Comparator;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

import com.google.common.collect.Maps;


@ConfigurationProperties("platform.exception")
public class ExceptionProperties {

    private String defaultMoreInfoUrl;
    private int exceptionHandlerOrder = Ordered.LOWEST_PRECEDENCE;
    private Map<String, String> definitions = Maps.newTreeMap(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }
    });

    public String getDefaultMoreInfoUrl() {
        return defaultMoreInfoUrl;
    }

    public void setDefaultMoreInfoUrl(String defaultMoreInfoUrl) {
        this.defaultMoreInfoUrl = defaultMoreInfoUrl;
    }

    public Map<String, String> getDefinitions() {
        return definitions;
    }

    public int getExceptionHandlerOrder() {
        return exceptionHandlerOrder;
    }

    public void setExceptionHandlerOrder(int exceptionHandlerOrder) {
        this.exceptionHandlerOrder = exceptionHandlerOrder;
    }
}
