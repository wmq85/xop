package com.mqoo.platform.xop.example.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mqoo.platform.xop.common.web.BaseController;
import com.mqoo.platform.xop.example.dubbo.model.EchoInfo;
import com.mqoo.platform.xop.example.dubbo.service.EchoInfoService;

/**
 * echo controller
 * 
 * @author mingqi.wang
 *
 */
@RestController
public class EchoController extends BaseController {
    @Reference(version = "1.0.0")
    public EchoInfoService echoInfoService;

    @RequestMapping("/echo")
    EchoInfo ex(HttpServletRequest request) {
        String randomStr = RandomStringUtils.randomNumeric(15);
        EchoInfo info = echoInfoService.echo(randomStr);
        return info;
    }

}
