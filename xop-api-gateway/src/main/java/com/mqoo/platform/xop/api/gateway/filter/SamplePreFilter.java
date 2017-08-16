package com.mqoo.platform.xop.api.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class SamplePreFilter extends ZuulFilter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestUri=request.getRequestURI();
		String qs=request.getQueryString();
		LOG.info(requestUri+(StringUtils.isEmpty(qs)?"":"?"+qs));
		//add header
		ctx.addZuulRequestHeader("X-ZUUL-VERSION", "1.0"); 
		//ctx.setSendZuulResponse(false);
        return null;
    }
}