package com.vinsguru.webfluxpatterns.sec04.service;

import com.vinsguru.webfluxpatterns.sec04.client.UserClient;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class PaymentOrchestrator extends Orchestrator {

    @Autowired
    private UserClient client;

    @Override
    public Mono<OrchestationRequestContext> create(OrchestationRequestContext context) {
        return this.client.deduct(context.getPaymentRequest())
                .doOnNext(context::setPaymentResponse)
                .thenReturn(context)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestationRequestContext> isSuccess() {
        return context -> Objects.nonNull(context.getPaymentResponse())
                && Status.SUCCESS.equals(context.getPaymentResponse().status());
    }

    /**
     * Cancela solo si hubo un pago exitoso (usa el filter)
     */
    @Override
    public Consumer<OrchestationRequestContext> cancel() {
        return context -> Mono.just(context)
                .filter(isSuccess())
                .map(OrchestationRequestContext::getPaymentRequest)
                .flatMap(this.client::refund)
                .subscribe();
    }
}
