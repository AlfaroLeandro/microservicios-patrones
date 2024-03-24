package com.vinsguru.webfluxpatterns.sec04.service;

import com.vinsguru.webfluxpatterns.sec04.client.ProductClient;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.Product;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import com.vinsguru.webfluxpatterns.sec04.util.OrchestationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFullfillmentService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PaymentOrchestrator paymentOrchestrator;

    @Autowired
    private InventoryOrchestrator inventoryOrchestrator;

    @Autowired
    private ShippingOrchestrator shippingOrchestrator;

    public Mono<OrchestationRequestContext> placeOrder(OrchestationRequestContext context) {
        return this.getProduct(context)
                .doOnNext(OrchestationUtil::buildPaymentRequest)
                .flatMap(this.paymentOrchestrator::create)
                .doOnNext(OrchestationUtil::buildInventoryRequest)
                .flatMap(this.inventoryOrchestrator::create)
                .doOnNext(OrchestationUtil::buildShippingRequest)
                .flatMap(this.shippingOrchestrator::create)
                .doOnNext(c -> c.setStatus(Status.SUCCESS))
                .doOnError(_ -> context.setStatus(Status.FAILED))
                .onErrorReturn(context);
    }

    private Mono<OrchestationRequestContext> getProduct(OrchestationRequestContext context) {
        return this.productClient.getProduct(Long.valueOf(context.getOrderRequest().productId()))
                .map(Product::price)
                .doOnNext(context::setProductPrice)
                .map(_ -> context); //evita que devuelva el contexto con el precio vacio
    }
}
