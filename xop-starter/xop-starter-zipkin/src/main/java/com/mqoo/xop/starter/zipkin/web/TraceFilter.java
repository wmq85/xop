package com.mqoo.xop.starter.zipkin.web;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.mqoo.xop.starter.zipkin.context.TraceContext;
import com.mqoo.xop.starter.zipkin.trace.TraceAgent;
import com.mqoo.xop.starter.zipkin.trace.config.TraceConfig;
import com.mqoo.xop.starter.zipkin.utils.IdUtils;
import com.mqoo.xop.starter.zipkin.utils.NetworkUtils;
import com.twitter.zipkin.gen.Annotation;
import com.twitter.zipkin.gen.Endpoint;
import com.twitter.zipkin.gen.Span;

/**
 * Author: haolin Email: haolin.h0@gmail.com
 */
public class TraceFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(TraceFilter.class);

    private TraceConfig traceConfig;

    private TraceAgent agent;

    public TraceFilter(TraceConfig traceConfig) {
        this.traceConfig = traceConfig;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

        if (!traceConfig.isEnabled()) {
            return;
        }
        agent = new TraceAgent(traceConfig.getZipkinUrl());
        log.info("init the trace filter with config({}).", new Object[] {config});
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!traceConfig.isEnabled()) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        
        // do trace
        Stopwatch watch = Stopwatch.createStarted();

        // start root span
        Span rootSpan = startTrace(req, uri);

        // prepare trace context
        TraceContext.start();
        TraceContext.setTraceId(rootSpan.getTrace_id());
        TraceContext.setSpanId(rootSpan.getId());
        TraceContext.addSpan(rootSpan);

        // executor other filters
        chain.doFilter(request, response);

        // end root span
        endTrace(req, rootSpan, watch);

    }

    private Span startTrace(HttpServletRequest req, String point) {

        String apiName = req.getRequestURI();
        Span apiSpan = new Span();

        // span basic data
        long id = IdUtils.get();
        apiSpan.setId(id);
        apiSpan.setTrace_id(id);
        apiSpan.setName(TraceContext.getTraceConfig().getApplicationName());
        long timestamp = System.nanoTime();
        apiSpan.setTimestamp(timestamp);

        // sr annotation
        apiSpan.addToAnnotations(Annotation.create(timestamp, TraceContext.ANNO_SR, Endpoint.create(
                apiName, NetworkUtils.ip2Num(NetworkUtils.getSiteIp()), req.getLocalPort())));

        return apiSpan;
    }

    private void endTrace(HttpServletRequest req, Span span, Stopwatch watch) {
        // ss annotation
        span.addToAnnotations(Annotation.create(System.nanoTime(), TraceContext.ANNO_SS,
                Endpoint.create(span.getName(), NetworkUtils.ip2Num(NetworkUtils.getSiteIp()),
                        req.getLocalPort())));

        span.setDuration(watch.stop().elapsed(TimeUnit.MICROSECONDS));

        // send trace spans
        agent.send(TraceContext.getSpans());
    }



    @Override
    public void destroy() {
        // clear trace context
        TraceContext.clear();
    }
}
