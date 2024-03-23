package com.vinsguru.webfluxpatterns.sec03.service;

import com.vinsguru.webfluxpatterns.sec03.client.ProductClient;
import com.vinsguru.webfluxpatterns.sec03.dto.*;
import com.vinsguru.webfluxpatterns.sec03.util.DebugUtil;
import com.vinsguru.webfluxpatterns.sec03.util.OrchestationUtil;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrchestratorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderFullfillmentService fullfillmentService;

    @Autowired
    private OrderCancellationService cancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono) {
        return mono
                .map(OrchestationRequestContext::new)
                .flatMap(this::getProduct)
                .doOnNext(OrchestationUtil::buildRequestContext)
                .flatMap(fullfillmentService::placeOrder)
                .doOnNext(this::doOrderPostProcessing)
                .doOnNext(DebugUtil::print)
                .map(this::toOrderResponse);
    }

    private void doOrderPostProcessing(OrchestationRequestContext context) {
        if(Status.FAILED.equals(context.getStatus()))
            this.cancellationService.cancelOrder(context);
    }

    private OrderResponse toOrderResponse(OrchestationRequestContext context) {
        var isSuccess = Status.SUCCESS.equals(context.getStatus());
        var address = isSuccess ? context.getShippingResponse().address() : null;
        var deliveryDate = isSuccess ? context.getShippingResponse().expectedDelivery() : null;

        return new OrderResponse(
                context.getOrderRequest().userId(),
                context.getOrderRequest().productId(),
                context.getOrderId(),
                context.getStatus(),
                address,
                deliveryDate
        );
    }

    private Mono<OrchestationRequestContext> getProduct(OrchestationRequestContext context) {
        return this.productClient.getProduct(Long.valueOf(context.getOrderRequest().productId()))
                .map(Product::price)
                .doOnNext(context::setProductPrice)
                .thenReturn(context);
    }

}
