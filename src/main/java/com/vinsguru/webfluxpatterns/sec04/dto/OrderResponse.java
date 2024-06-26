package com.vinsguru.webfluxpatterns.sec04.dto;

import java.util.UUID;

public record OrderResponse(

        Integer userId,
        Integer productId,
        UUID orderId,
        Status status,
        Address shippingAddress,
        String expectedDelivery

) {
}
