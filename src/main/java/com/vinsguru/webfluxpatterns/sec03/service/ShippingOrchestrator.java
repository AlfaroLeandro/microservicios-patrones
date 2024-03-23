package com.vinsguru.webfluxpatterns.sec03.service;

import com.vinsguru.webfluxpatterns.sec03.client.ShippingClient;
import com.vinsguru.webfluxpatterns.sec03.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ShippingOrchestrator extends Orchestrator{
    @Autowired
    private ShippingClient client;

    @Override
    public Mono<OrchestationRequestContext> create(OrchestationRequestContext context) {
        return this.client.schedule(context.getShippingRequest())
                .doOnNext(context::setShippingResponse)
                .thenReturn(context);
    }

    @Override
    public Predicate<OrchestationRequestContext> isSuccess() {
        return context -> Status.SUCCESS.equals(context.getShippingResponse().status());
    }

    @Override
    public Consumer<OrchestationRequestContext> cancel() {
        return context -> Mono.just(context)
                .filter(isSuccess())
                .map(OrchestationRequestContext::getShippingRequest)
                .flatMap(this.client::cancel)
                .subscribe();
    }
}
