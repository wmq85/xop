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
 * 签名顺序<br>
 * 1、http mothod，小写<br>
 * 2、requestUri：http请求url,包含query string<br>
 * 3、appKey<br>
 * 4、timestamp:时间戳，精确到毫秒<br>
 * 5、token: 用户登录token<br>
 * 6、requestBody：请求体<br>
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
            String method = request.getMethod().toLowerCase();
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
                throw new SecurityValidateException("miss timestamp header.");
            }
            if (StringUtils.isEmpty(signature)) {
                throw new SecurityValidateException("miss signature header.");
            }
            if (StringUtils.isEmpty(appKey)) {
                throw new SecurityValidateException("miss authorization header contained app key.");
            }
            //
            // 1、http mothod，小写<br>
            // 2、requestUri：http请求url,包含query string<br>
            // 3、appKey<br>
            // 4、timestamp:时间戳，精确到毫秒<br>
            // 5、token: 用户登录token<br>
            // 6、requestBody：请求体<br>
            StringBuffer sbf = new StringBuffer();
            //@formatter:off
            sbf.append(method)
               .append("\n")
               .append(requestUri)
               .append("\n")
               .append(appKey)
               .append("\n")
               .append(time)
               .append("\n")
               .append(StringUtils.defaultString(token))
               .append("\n")
               .append(requestBody)
               ;
            //@formatter:on
            //
            String signStr = sbf.toString();
            LOG.debug("signature from request:{}", signature);
            LOG.debug("signature data :\n{}", signStr);
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
        String queryStrWithoutToken =
                StringUtils.replacePattern(StringUtils.defaultString(queryStr), p, "");
        return queryStrWithoutToken;
    }
}
