package com.vinsguru.webfluxpatterns.sec04.service;

import com.vinsguru.webfluxpatterns.sec04.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.exception.OrderFulfillmentFailure;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Orchestrator {

    public abstract Mono<OrchestationRequestContext> create(OrchestationRequestContext context);
    public abstract Predicate<OrchestationRequestContext> isSuccess();
    public abstract Consumer<OrchestationRequestContext> cancel();
    protected BiConsumer<OrchestationRequestContext, SynchronousSink<OrchestationRequestContext>> statusHandler() {
        return (context, sink) -> {
            if(isSuccess().test(context))
                sink.next(context);
            else
                sink.error(new OrderFulfillmentFailure());
        };
    }

}
