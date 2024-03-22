package com.vinsguru.webfluxpatterns.sec03.dto;

public record ProductResponse(
        Integer id,
        String category,
        String description,
        Integer price
) {
}
