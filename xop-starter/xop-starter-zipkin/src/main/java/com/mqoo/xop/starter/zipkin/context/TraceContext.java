package com.mqoo.xop.starter.zipkin.context;

import java.util.ArrayList;
import java.util.List;

import com.mqoo.xop.starter.zipkin.trace.config.TraceConfig;
import com.twitter.zipkin.gen.Span;

public class TraceContext extends AbstractContext {

    private static ThreadLocal<Long> TRACE_ID = new InheritableThreadLocal<>();

    private static ThreadLocal<Long> SPAN_ID = new InheritableThreadLocal<>();

    private static ThreadLocal<List<Span>> SPAN_LIST = new InheritableThreadLocal<>();

    public static final String TRACE_ID_KEY = "traceId";

    public static final String SPAN_ID_KEY = "spanId";

    public static final String ANNO_CS = "cs";

    public static final String ANNO_CR = "cr";

    public static final String ANNO_SR = "sr";

    public static final String ANNO_SS = "ss";

    private static TraceConfig traceConfig;

    public static TraceConfig getTraceConfig(){
        return traceConfig;
    }

    public static void setTraceConfig(TraceConfig config){
        traceConfig=config;
    }

    private TraceContext(){}

    public static void setTraceId(Long traceId){
        TRACE_ID.set(traceId);
    }

    public static Long getTraceId(){
        return TRACE_ID.get();
    }

    public static Long getSpanId() {
        return SPAN_ID.get();
    }

    public static void setSpanId(Long spanId) {
        SPAN_ID.set(spanId);
    }

    public static void addSpan(Span span){
        SPAN_LIST.get().add(span);
    }

    public static List<Span> getSpans(){
        return SPAN_LIST.get();
    }

    public static void clear(){
        TRACE_ID.remove();
        SPAN_ID.remove();
        SPAN_LIST.remove();
    }

    public static void init(TraceConfig traceConfig) {
        setTraceConfig(traceConfig);
    }

    public static void start(){
        clear();
        SPAN_LIST.set(new ArrayList<Span>());
    }

}
