package com.mqoo.xop.starter.security.validator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mqoo.xop.starter.security.SecurityProperties;
import com.mqoo.xop.starter.security.SecurityValidateContext;
import com.mqoo.xop.starter.security.exception.SecurityValidateException;
import com.mqoo.xop.starter.security.service.AppValidateService;
import com.mqoo.xop.starter.security.validator.SecurityValidator;

/**
 * app 状态验证
 *
 * @author mingqi.wang
 * @since 2017/7/4
 */
public class AppStatusValidator implements SecurityValidator {
    Logger LOG = LoggerFactory.getLogger(getClass());
    private AppValidateService appValidateService;
    private SecurityProperties securityProperties;

    public AppStatusValidator(AppValidateService appValidateService,
            SecurityProperties securityProperties) {
        this.appValidateService = appValidateService;
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean validate(SecurityValidateContext securityValidateContext) {
        String appKey = securityValidateContext.getAppKey();
        if (!securityProperties.isEnableAppStatusValidator()) {
            LOG.debug("AppStatusValidator disabled.");
        } else {
            if (!appValidateService.exists(appKey)) {
                throw new SecurityValidateException("app no found.");
            }
            if (!appValidateService.available(appKey)) {
                throw new SecurityValidateException("app disabled.");
            }
        }
        return true;
    }

    @Override
    public int order() {
        return 1;
    }

}
