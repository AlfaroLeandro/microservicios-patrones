package com.vinsguru.webfluxpatterns.sec04.service;

import com.vinsguru.webfluxpatterns.sec04.client.InventoryClient;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class InventoryOrchestrator extends Orchestrator {

    @Autowired
    private InventoryClient client;

    @Override
    public Mono<OrchestationRequestContext> create(OrchestationRequestContext context) {
        return this.client.deduct(context.getInventoryRequest())
                .doOnNext(context::setInventoryResponse)
                .thenReturn(context)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestationRequestContext> isSuccess() {
        return context -> Objects.nonNull(context.getInventoryResponse())
                && Status.SUCCESS.equals(context.getInventoryResponse().status());
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
