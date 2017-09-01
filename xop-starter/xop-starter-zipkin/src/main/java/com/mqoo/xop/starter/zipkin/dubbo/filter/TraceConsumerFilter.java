package com.mqoo.xop.starter.zipkin.dubbo.filter;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.Tracer;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Stopwatch;
import com.mqoo.xop.starter.spring.util.SpringContextUtil;
import com.mqoo.xop.starter.zipkin.context.TraceContext;
import com.mqoo.xop.starter.zipkin.trace.TraceAgent;
import com.mqoo.xop.starter.zipkin.utils.IdUtils;
import com.mqoo.xop.starter.zipkin.utils.NetworkUtils;
import com.twitter.zipkin.gen.Annotation;
import com.twitter.zipkin.gen.Endpoint;
import com.twitter.zipkin.gen.Span;

/*
* 消费端日志过滤器
* 作者：姜敏
* 版本：V1.0
* 创建日期：2017/4/13
* 修改日期:2017/4/13
*/
@Activate
public class TraceConsumerFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    //private TraceAgent traceAgent=new TraceAgent(TraceContext.getTraceConfig().getZipkinUrl());

    /**
     * 从sleuth中获取traceId
     * @return
     */
    private org.springframework.cloud.sleuth.Span getSleuthSpan(){
         Tracer tracer=SpringContextUtil.getBean(Tracer.class);
         if(tracer!=null){
             return tracer.getCurrentSpan();
         }
         return null;
    }
    
    private Span startTrace(Invoker<?> invoker, Invocation invocation) {

        Span consumerSpan = new Span();

        org.springframework.cloud.sleuth.Span sleuthSpan=getSleuthSpan();
        Long traceId=null;
        Long parentSpanId=null;
        Long spanId=IdUtils.get();
        if(isTrace()){
            traceId=sleuthSpan.getTraceId();
            parentSpanId=sleuthSpan.getSpanId();
            TraceContext.setTraceId(traceId);
            TraceContext.setSpanId(spanId);
        }
        consumerSpan.setId(spanId);
        consumerSpan.setTrace_id(TraceContext.getTraceId());
        consumerSpan.setParent_id(parentSpanId);
        String serviceName = invoker.getInterface().getSimpleName() + "." + invocation.getMethodName();
        consumerSpan.setName(serviceName);
        //consumerSpan.setName(TraceContext.getTraceConfig().getApplicationName());
        long timestamp = System.currentTimeMillis()*1000;
        consumerSpan.setTimestamp(timestamp);

        consumerSpan.addToAnnotations(
                Annotation.create(timestamp, TraceContext.ANNO_CS,
                        Endpoint.create(
                                TraceContext.getTraceConfig().getApplicationName(),
                                NetworkUtils.ip2Num(NetworkUtils.getSiteIp()),
                                TraceContext.getTraceConfig().getServerPort() )));

        Map<String, String> attaches = invocation.getAttachments();
        attaches.put(TraceContext.TRACE_ID_KEY, String.valueOf(consumerSpan.getTrace_id()));
        attaches.put(TraceContext.SPAN_ID_KEY, String.valueOf(consumerSpan.getId()));
        return consumerSpan;
    }

    private void endTrace(Span span, Stopwatch watch) {

        span.addToAnnotations(
                Annotation.create(System.currentTimeMillis()*1000, TraceContext.ANNO_CR,
                        Endpoint.create(
                                span.getName(),
                                NetworkUtils.ip2Num(NetworkUtils.getSiteIp()),
                                TraceContext.getTraceConfig().getServerPort())));

        span.setDuration(watch.stop().elapsed(TimeUnit.MICROSECONDS));
        TraceAgent traceAgent=new TraceAgent(TraceContext.getTraceConfig().getZipkinUrl());

        traceAgent.send(TraceContext.getSpans());

    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if(!TraceContext.getTraceConfig().isEnabled() || !isTrace()){
            return invoker.invoke(invocation);
        }

        Stopwatch watch = Stopwatch.createStarted();
        Span span= this.startTrace(invoker,invocation);
        TraceContext.start();
        TraceContext.setTraceId(span.getTrace_id());
        TraceContext.setSpanId(span.getId());
        TraceContext.addSpan(span);
        Result result = invoker.invoke(invocation);
        this.endTrace(span,watch);

        return result;
    }
    
    private boolean isTrace(){
        org.springframework.cloud.sleuth.Span sleuthSpan=getSleuthSpan();
        return sleuthSpan!=null && sleuthSpan.isExportable();
    }
}
