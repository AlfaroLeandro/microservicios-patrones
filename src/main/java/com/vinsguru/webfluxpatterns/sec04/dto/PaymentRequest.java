package com.vinsguru.webfluxpatterns.sec04.dto;

import java.util.UUID;

public record PaymentRequest(

        Integer userId,
        Integer amount,
        UUID orderId
) {
}
