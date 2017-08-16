package com.mqoo.platform.xop.support.security.validator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mqoo.platform.xop.common.exception.app.AppDisabledException;
import com.mqoo.platform.xop.support.security.SecurityProperties;
import com.mqoo.platform.xop.support.security.SecurityValidateContext;
import com.mqoo.platform.xop.support.security.service.AppValidateService;
import com.mqoo.platform.xop.support.security.validator.SecurityValidator;

/**
 * app 状态验证
 *
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Component
public class AppStatusValidator implements SecurityValidator {
	Logger LOG=LoggerFactory.getLogger(getClass());
	@Autowired
	private AppValidateService appValidateService;
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public boolean validate(SecurityValidateContext securityValidateContext) {
		String appKey=securityValidateContext.getAppKey();
		if(!securityProperties.isEnableAppStatusValidator()){
			LOG.debug("AppStatusValidator disabled.");
		}else{
			if(!appValidateService.exists(appKey)){
				throw new AppDisabledException("app no found.");
			}
			if(!appValidateService.available(appKey)){
				throw new AppDisabledException("app disabled.");
			}
		}
		return true;
	}

	@Override
	public int order() {
		return 1;
	}

}
