package com.vinsguru.webfluxpatterns.sec01.dto;

public record ProductResponse(
        Long id,
        String category,
        String description,
        Integer price
) {
}
