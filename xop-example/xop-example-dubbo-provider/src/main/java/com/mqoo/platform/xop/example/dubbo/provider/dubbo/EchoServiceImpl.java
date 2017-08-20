package com.mqoo.platform.xop.example.dubbo.provider.dubbo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.alibaba.dubbo.config.annotation.Service;
import com.mqoo.platform.xop.common.data.PageInfo;
import com.mqoo.platform.xop.common.util.GsonUtil;
import com.mqoo.platform.xop.example.dubbo.entity.Country;
import com.mqoo.platform.xop.example.dubbo.model.EchoInfo;
import com.mqoo.platform.xop.example.dubbo.provider.service.CountryService;
import com.mqoo.platform.xop.example.dubbo.service.EchoInfoService;

@Service(version = "1.0.0")
public class EchoServiceImpl implements EchoInfoService {
    Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CountryService countryService;

    public EchoInfo echo(String str) {
        LOG.info(str);
        List<Country> content = countryService.findAll();
        EchoInfo echoInfo = new EchoInfo();
        echoInfo.setMessage(GsonUtil.toJson(content));
        return echoInfo;
    }

    public List<Country> contries() {
        return countryService.findAll();
    }

    public PageInfo<Country> contriesPage() {
        return countryService.findAll(new PageRequest(0, 10));
    }
}
