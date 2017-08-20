package com.mqoo.platform.xop.api.gateway.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.mqoo.platform.xop.common.web.BufferedRequestWrapper;
import com.mqoo.xop.starter.security.SecurityValidateContext;
import com.mqoo.xop.starter.security.validator.SecurityValidatorHandler;
import com.mqoo.xop.starter.spring.util.SpringContextUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * zuul 安全过滤器
 * <p>
 * 处理api请求验证签名等
 * 
 * @author mingqi.wang
 * @since 2017/8/17
 */
public class SecurityFilter extends ZuulFilter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //
        LOG.debug("SecurityFilter start>>>");
        BufferedRequestWrapper bufferedRequest = null;
        try {
            bufferedRequest = new BufferedRequestWrapper(request);
        } catch (IOException e) {
            // ignore
        }
        SecurityValidateContext securityValidateContext =
                new SecurityValidateContext(bufferedRequest);
        SecurityValidatorHandler securityValidatorHandler =
                SpringContextUtil.getBean(SecurityValidatorHandler.class);
        securityValidatorHandler.handle(securityValidateContext);
        //
        return null;
    }
}
