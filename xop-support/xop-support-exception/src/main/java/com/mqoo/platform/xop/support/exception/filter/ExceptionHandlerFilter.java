package com.mqoo.platform.xop.support.exception.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 异常处理Filter
 * <p>
 * 在Filter、Interceptor中抛出的异常无法被HandlerExceptionResolver拦截处理,在此处处理
 * 
 *@see org.springframework.web.servlet.HandlerExceptionResolver#resolveException
 *@author mingqi.wang
 */
public class ExceptionHandlerFilter extends OncePerRequestFilter {
	HandlerExceptionResolver handlerExceptionResolver;
	
    public ExceptionHandlerFilter(HandlerExceptionResolver handlerExceptionResolver) {
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			logger.debug("exceptionHandlerFilter handler exception",e);
			handlerExceptionResolver.resolveException(request, response, null, e);
		}
    }

}
