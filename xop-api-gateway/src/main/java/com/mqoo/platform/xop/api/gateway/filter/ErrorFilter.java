package com.mqoo.platform.xop.api.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.ReflectionUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class ErrorFilter extends ZuulFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorFilter.class);
    
	@Override
	public int filterOrder() {
		return FilterConstants.SEND_ERROR_FILTER_ORDER-1;
	}

	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	@Override
	public boolean shouldFilter() {
	    RequestContext ctx = RequestContext.getCurrentContext();
	    // only forward to errorPath if it hasn't been forwarded to already
        return !ctx.containsKey("javax.servlet.error.status_code");
	    //return true;
	}
    @Override
    public Object run() {
        /*try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object e = ctx.getThrowable();

            if (e != null && e instanceof ZuulException) {
                ZuulException zuulException = (ZuulException)e;
                LOG.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);

                // Remove error code to prevent further error handling in follow up filters
                ctx.remove("error.status_code");

                // Populate context with new response values
                ctx.setResponseBody("Overriding Zuul Exception Body");
                ctx.getResponse().setContentType("application/json");
                ctx.setResponseStatusCode(500); //Can set any error code as excepted
                ctx.getResponse().r
            }
        }
        catch (Exception ex) {
            //SendErrorFilter  e;
            LOG.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }*/
        return null;
    }
}