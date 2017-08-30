package com.mqoo.xop.starter.zipkin.context;

public abstract class AbstractContext {

    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
