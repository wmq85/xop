package com.mqoo.platform.xop.example.dubbo.service;

import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.example.dubbo.model.EchoInfo;

public interface EchoInfoService {
    EchoInfo echo(String str) throws BaseException;
}
