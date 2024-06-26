package com.vinsguru.webfluxpatterns.sec04.dto;

import java.util.UUID;

public record ShippingRequest(

        Integer quantity,
        Integer userId,
        UUID inventoryId

) {
}
