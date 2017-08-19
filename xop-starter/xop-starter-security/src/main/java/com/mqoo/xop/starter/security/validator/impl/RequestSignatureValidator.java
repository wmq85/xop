package com.mqoo.xop.starter.security.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mqoo.platform.xop.common.web.BufferedRequestWrapper;
import com.mqoo.platform.xop.common.web.api.Constants;
import com.mqoo.xop.starter.security.SecurityProperties;
import com.mqoo.xop.starter.security.SecurityValidateContext;
import com.mqoo.xop.starter.security.exception.SecurityValidateException;
import com.mqoo.xop.starter.security.service.AppValidateService;
import com.mqoo.xop.starter.security.validator.SecurityValidator;

/**
 * 接口请求签名验证
 * <p>
 * 签名规则：RSA(requestBody + time + requestUri+ token)<br>
 * requestBody：http请求体<br>
 * time:时间戳，精确到毫秒<br>
 * requestUri：http请求url,包含query string<br>
 * token: 当token存在时,需加入签名字符串
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public class RequestSignatureValidator implements SecurityValidator {
    Logger LOG = LoggerFactory.getLogger(getClass());
    private AppValidateService appValidateService;
    private SecurityProperties securityProperties;

    public RequestSignatureValidator(AppValidateService appValidateService,
            SecurityProperties securityProperties) {
        this.appValidateService = appValidateService;
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean validate(SecurityValidateContext securityValidateContext) {
        BufferedRequestWrapper request = securityValidateContext.getRawRequest();
        String requestUri = request.getRequestURI();
        if (!securityProperties.isEnableSignatureValidator()) {
            LOG.debug("SignatureValidator disabled.");
        } else {
            //
            String queryString = request.getQueryString();
            String queryStringNoToken = removeTokenParamFromURI(queryString);
            if (!StringUtils.isEmpty(queryStringNoToken)) {
                requestUri = String.format("%s?%s", requestUri, queryStringNoToken);
            }
            //
            String requestBody = "";
            try {
                requestBody = request.getRequestBody();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
            //
            String time = securityValidateContext.getTimestamp();
            String signature = securityValidateContext.getSignature();
            String appKey = securityValidateContext.getAppKey();
            String token = securityValidateContext.getToken();
            if (StringUtils.isEmpty(time)) {
                throw new SecurityValidateException("miss X-Request-Timestamp header.");
            }
            if (StringUtils.isEmpty(signature)) {
                throw new SecurityValidateException("miss X-Request-Signature header.");
            }
            if (StringUtils.isEmpty(appKey)) {
                throw new SecurityValidateException("miss app key.");
            }
            //
            String signStr = requestBody + time + requestUri + StringUtils.defaultString(token);
            boolean verify = appValidateService.signVerify(appKey, signStr, signature);
            if (!verify) {
                throw new SecurityValidateException("illegal request signature.");
            }
        }
        return true;
    }

    @Override
    public int order() {
        return 2;
    }

    private String removeTokenParamFromURI(String queryStr) {
        String p = "&{0,1}" + Constants.HttpParam.ACCESS_TOKEN + "=[\\w-_]*";
        String queryStrWithoutToken = StringUtils.replacePattern(queryStr, p, "");
        return queryStrWithoutToken;
    }
}
