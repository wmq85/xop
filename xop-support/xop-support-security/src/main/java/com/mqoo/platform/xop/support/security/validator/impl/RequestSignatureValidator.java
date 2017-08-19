package com.mqoo.platform.xop.support.security.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mqoo.platform.xop.common.exception.authorization.AuthorizationException;
import com.mqoo.platform.xop.common.web.BufferedRequestWrapper;
import com.mqoo.platform.xop.common.web.api.Constants;
import com.mqoo.platform.xop.support.security.SecurityProperties;
import com.mqoo.platform.xop.support.security.SecurityValidateContext;
import com.mqoo.platform.xop.support.security.service.AppValidateService;
import com.mqoo.platform.xop.support.security.validator.SecurityValidator;

/**
 * 接口请求签名验证
 * <p>
 * 签名规则：RSA(requestBody + time + requestUri)<br>
 * requestBody：http请求体<br>
 * time:时间戳，精确到毫秒<br>
 * requestUri：http请求url,包含query string
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Component
public class RequestSignatureValidator implements SecurityValidator {
	Logger LOG=LoggerFactory.getLogger(getClass());
	public final static String BEARER = "Bearer ";
	@Autowired
    private AppValidateService appValidateService;
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public boolean validate(SecurityValidateContext securityValidateContext) {
		BufferedRequestWrapper request = securityValidateContext.getRawRequest();
		String requestUri = request.getRequestURI();
		if(!securityProperties.isEnableSignatureValidator()){
            LOG.debug("SignatureValidator disabled.");
        }else {
			//
			String queryString = request.getQueryString();
			if (!StringUtils.isEmpty(queryString)) {
				requestUri = String.format("%s?%s", requestUri, queryString);
			}
			//
			String requestBody="";
			try {
				requestBody = request.getRequestBody();
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
			//
			String time = request.getHeader(Constants.HttpHeader.HEADER_REQUEST_TIMESTAMP);
			String signature = request.getHeader(Constants.HttpHeader.HEADER_REQUEST_SIGNATURE);
			String bearer = request.getHeader(Constants.HttpHeader.HEADER_AUTHORIZATION);
			if (StringUtils.isEmpty(time)) {
				throw new AuthorizationException("miss X-Request-Timestamp header.");
			}
			if (StringUtils.isEmpty(signature)) {
				throw new AuthorizationException("miss X-Request-Signature header.");
			}
			if (StringUtils.isEmpty(bearer)) {
				throw new AuthorizationException("miss Authorization header.");
			}
			String appKey = bearer.replaceAll(BEARER, "");
			//
			String signStr = requestBody + time + requestUri;
			boolean verify = appValidateService.signVerify(appKey, signStr, signature);
			if (!verify) {
				throw new AuthorizationException("illegal request signature.");
			}
		}
		return true;
	}

	@Override
	public int order() {
		return 2;
	}
}
