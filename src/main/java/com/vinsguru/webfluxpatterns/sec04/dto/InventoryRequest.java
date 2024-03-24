package com.vinsguru.webfluxpatterns.sec04.dto;

import java.util.UUID;

public record InventoryRequest(

    UUID paymentId,
    Integer productId,
    Integer quantity

) {
}
