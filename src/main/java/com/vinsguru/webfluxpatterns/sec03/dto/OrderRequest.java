package com.vinsguru.webfluxpatterns.sec03.dto;

public record OrderRequest(
        Integer userId,
        Integer productId,
        Integer quantity
) {
}
