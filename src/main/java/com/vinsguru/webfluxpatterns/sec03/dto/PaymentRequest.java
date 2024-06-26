package com.vinsguru.webfluxpatterns.sec03.dto;

import java.util.UUID;

public record PaymentRequest(

        Integer userId,
        Integer amount,
        UUID orderId
) {
}
