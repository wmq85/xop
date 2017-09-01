package com.mqoo.xop.starter.zipkin.trace.collector;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.kristofa.brave.SpanCollectorMetricsHandler;

public class SimpleMetricsHandler implements SpanCollectorMetricsHandler {

    final AtomicInteger acceptedSpans = new AtomicInteger();
    final AtomicInteger droppedSpans = new AtomicInteger();

    @Override
    public void incrementAcceptedSpans(int quantity) {
        acceptedSpans.addAndGet(quantity);
    }

    @Override
    public void incrementDroppedSpans(int quantity) {
        droppedSpans.addAndGet(quantity);
    }
}