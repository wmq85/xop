package com.mqoo.xop.starter.security.validator.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mqoo.xop.starter.security.SecurityProperties;
import com.mqoo.xop.starter.security.SecurityValidateContext;
import com.mqoo.xop.starter.security.exception.SecurityValidateException;
import com.mqoo.xop.starter.security.validator.SecurityValidator;

/**
 * 请求时间戳验证
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public class TimestampValidator implements SecurityValidator {
    Logger LOG = LoggerFactory.getLogger(getClass());
    public static final Integer EXPIRE_SECONDS = 5 * 60 * 1000;
    private SecurityProperties securityProperties;

    public TimestampValidator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean validate(SecurityValidateContext securityValidateContext) {
        String timeStr = securityValidateContext.getTimestamp();
        if (!securityProperties.isEnableRequestTimestampValidator()) {
            LOG.debug("RequestTimestampValidator disabled.");
        } else {
            if (!NumberUtils.isNumber(timeStr)) {
                throw new SecurityValidateException("timestamp request header is not a number.");
            }
            Long time = Long.valueOf(timeStr);
            Long now = System.currentTimeMillis();
            if (Math.abs(now - time) > EXPIRE_SECONDS) {
                throw new SecurityValidateException("timestamp out of date.");
            }
        }

        return true;
    }

    @Override
    public int order() {
        return 0;
    }
}
