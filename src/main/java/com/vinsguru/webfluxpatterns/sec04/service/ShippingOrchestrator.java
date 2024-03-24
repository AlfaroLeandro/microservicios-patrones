package com.vinsguru.webfluxpatterns.sec04.service;

import com.vinsguru.webfluxpatterns.sec04.client.ShippingClient;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class ShippingOrchestrator extends Orchestrator {
    @Autowired
    private ShippingClient client;

    @Override
    public Mono<OrchestationRequestContext> create(OrchestationRequestContext context) {
        return this.client.schedule(context.getShippingRequest())
                .doOnNext(context::setShippingResponse)
                .thenReturn(context)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestationRequestContext> isSuccess() {
        return context -> Objects.nonNull(context.getShippingResponse())
                && Status.SUCCESS.equals(context.getShippingResponse().status());
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
