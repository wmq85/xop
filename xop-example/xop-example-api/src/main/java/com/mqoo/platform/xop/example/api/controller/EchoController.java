package com.mqoo.platform.xop.example.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mqoo.platform.xop.common.data.PageInfo;
import com.mqoo.platform.xop.common.web.BaseController;
import com.mqoo.platform.xop.example.dubbo.entity.Country;
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

    @RequestMapping("/echo/countries")
    List<Country> countries(HttpServletRequest request) {
        List<Country> info = echoInfoService.contries();
        return info;
    }

    @RequestMapping("/echo/countries/page")
    PageInfo<Country> countriesPage(HttpServletRequest request) {
        PageInfo<Country> info = echoInfoService.contriesPage();
        return info;
    }

}
