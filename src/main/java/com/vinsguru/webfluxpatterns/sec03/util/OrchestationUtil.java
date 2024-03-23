package com.vinsguru.webfluxpatterns.sec03.util;

import com.vinsguru.webfluxpatterns.sec03.dto.InventoryRequest;
import com.vinsguru.webfluxpatterns.sec03.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec03.dto.PaymentRequest;
import com.vinsguru.webfluxpatterns.sec03.dto.ShippingRequest;

public class OrchestationUtil {

    public static void buildRequestContext(OrchestationRequestContext context) {
        buildPaymentRequest(context);
        buildInventoryRequest(context);
        buildShippingRequest(context);
    }

    private static void buildPaymentRequest(OrchestationRequestContext context) {
        context.setPaymentRequest(new PaymentRequest(
                context.getOrderRequest().userId(),
                context.getProductPrice() * context.getOrderRequest().quantity(),
                context.getOrderId()));
    }

    public static void buildInventoryRequest(OrchestationRequestContext context) {
        context.setInventoryRequest(
                new InventoryRequest(
                        context.getOrderId(),
                        context.getOrderRequest().productId(),
                        context.getOrderRequest().quantity()
                ));
    }

    private static void buildShippingRequest(OrchestationRequestContext context) {
        context.setShippingRequest(
                new ShippingRequest(
                        context.getOrderRequest().quantity(),
                        context.getOrderRequest().userId(),
                        context.getOrderId()
                ));
    }

}
