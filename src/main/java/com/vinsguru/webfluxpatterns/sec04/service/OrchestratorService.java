package com.vinsguru.webfluxpatterns.sec04.service;

import com.vinsguru.webfluxpatterns.sec04.client.ProductClient;
import com.vinsguru.webfluxpatterns.sec04.dto.*;
import com.vinsguru.webfluxpatterns.sec04.util.DebugUtil;
import com.vinsguru.webfluxpatterns.sec04.util.OrchestationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrchestratorService {


    @Autowired
    private OrderFullfillmentService fullfillmentService;

    @Autowired
    private OrderCancellationService cancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono) {
        return mono
                .map(OrchestationRequestContext::new)
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

}
