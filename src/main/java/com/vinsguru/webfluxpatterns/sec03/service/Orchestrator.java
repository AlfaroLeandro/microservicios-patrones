package com.vinsguru.webfluxpatterns.sec03.service;

import com.vinsguru.webfluxpatterns.sec03.dto.OrchestationRequestContext;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Orchestrator {

    public abstract Mono<OrchestationRequestContext> create(OrchestationRequestContext context);
    public abstract Predicate<OrchestationRequestContext> isSuccess();
    public abstract Consumer<OrchestationRequestContext> cancel();

}
