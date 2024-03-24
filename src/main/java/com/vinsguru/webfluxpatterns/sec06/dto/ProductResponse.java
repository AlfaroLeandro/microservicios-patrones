package com.vinsguru.webfluxpatterns.sec06.dto;

public record ProductResponse(
        Long id,
        String category,
        String description,
        Integer price
) {
}
