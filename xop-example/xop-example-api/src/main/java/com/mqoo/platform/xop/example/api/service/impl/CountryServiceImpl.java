package com.mqoo.platform.xop.example.api.service.impl;

import org.springframework.stereotype.Service;

import com.mqoo.platform.xop.example.api.entity.Country;
import com.mqoo.platform.xop.example.api.mapper.CountryMapper;
import com.mqoo.platform.xop.example.api.service.CountryService;
import com.mqoo.xop.starter.data.mybatis.service.AbstractService;

@Service
public class CountryServiceImpl extends AbstractService<Country, Long, CountryMapper>
        implements CountryService {
}
