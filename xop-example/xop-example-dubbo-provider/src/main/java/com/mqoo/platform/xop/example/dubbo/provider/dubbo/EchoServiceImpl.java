package com.mqoo.platform.xop.example.dubbo.provider.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.mqoo.platform.xop.example.dubbo.model.EchoInfo;
import com.mqoo.platform.xop.example.dubbo.service.EchoInfoService;

@Service(version = "1.0.0")
public class EchoServiceImpl implements EchoInfoService {
    Logger LOG = LoggerFactory.getLogger(this.getClass());

    public EchoInfo echo(String str) {
        LOG.info(str);
        EchoInfo echoInfo = new EchoInfo();
        echoInfo.setMessage(str);
        return echoInfo;
    }
}
