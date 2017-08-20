package com.mqoo.platform.xop.example.dubbo.service;

import java.util.List;

import com.mqoo.platform.xop.common.data.PageInfo;
import com.mqoo.platform.xop.common.exception.BaseException;
import com.mqoo.platform.xop.example.dubbo.entity.Country;
import com.mqoo.platform.xop.example.dubbo.model.EchoInfo;

public interface EchoInfoService {
    EchoInfo echo(String str) throws BaseException;

    public List<Country> contries() throws BaseException;

    public PageInfo<Country> contriesPage() throws BaseException;
}
