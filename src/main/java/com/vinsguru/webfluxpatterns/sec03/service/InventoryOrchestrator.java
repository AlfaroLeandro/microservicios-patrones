package com.vinsguru.webfluxpatterns.sec03.service;

import com.vinsguru.webfluxpatterns.sec03.client.InventoryClient;
import com.vinsguru.webfluxpatterns.sec03.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class InventoryOrchestrator extends Orchestrator{

    @Autowired
    private InventoryClient client;

    @Override
    public Mono<OrchestationRequestContext> create(OrchestationRequestContext context) {
        return this.client.deduct(context.getInventoryRequest())
                .doOnNext(context::setInventoryResponse)
                .thenReturn(context);
    }

    @Override
    public Predicate<OrchestationRequestContext> isSuccess() {
        return context -> Status.SUCCESS.equals(context.getInventoryResponse().status());
    }

    @Override
    public Consumer<OrchestationRequestContext> cancel() {
        return context -> Mono.just(context)
                .filter(isSuccess())
                .map(OrchestationRequestContext::getInventoryRequest)
                .flatMap(this.client::restore)
                .subscribe();
    }
}
