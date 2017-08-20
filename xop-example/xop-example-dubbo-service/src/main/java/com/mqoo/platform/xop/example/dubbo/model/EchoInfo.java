package com.mqoo.platform.xop.example.dubbo.model;

import java.io.Serializable;

public class EchoInfo implements Serializable {
    private static final long serialVersionUID = 6993357410729564879L;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
