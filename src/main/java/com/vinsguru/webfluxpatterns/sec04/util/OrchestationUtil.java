package com.vinsguru.webfluxpatterns.sec04.util;

import com.vinsguru.webfluxpatterns.sec04.dto.InventoryRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.PaymentRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.ShippingRequest;

public class OrchestationUtil {

    public static void buildPaymentRequest(OrchestationRequestContext context) {
        context.setPaymentRequest(new PaymentRequest(
                context.getOrderRequest().userId(),
                context.getProductPrice() * context.getOrderRequest().quantity(),
                context.getOrderId()));
    }

    public static void buildInventoryRequest(OrchestationRequestContext context) {
        context.setInventoryRequest(
                new InventoryRequest(
                        context.getPaymentResponse().paymentId(),
                        context.getOrderRequest().productId(),
                        context.getOrderRequest().quantity()
                ));
    }

    public static void buildShippingRequest(OrchestationRequestContext context) {
        context.setShippingRequest(
                new ShippingRequest(
                        context.getOrderRequest().quantity(),
                        context.getOrderRequest().userId(),
                        context.getInventoryResponse().inventoryId()
                ));
    }

}
