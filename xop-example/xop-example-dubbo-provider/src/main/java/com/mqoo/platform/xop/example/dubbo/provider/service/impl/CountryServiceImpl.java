package com.mqoo.platform.xop.example.dubbo.provider.service.impl;

import org.springframework.stereotype.Service;

import com.mqoo.platform.xop.example.dubbo.provider.entity.Country;
import com.mqoo.platform.xop.example.dubbo.provider.mapper.CountryRepository;
import com.mqoo.platform.xop.example.dubbo.provider.service.CountryService;
import com.mqoo.xop.starter.data.jpa.service.AbstractService;

@Service
public class CountryServiceImpl extends AbstractService<Country, Long, CountryRepository>
        implements CountryService {
}
