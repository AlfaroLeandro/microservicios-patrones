package com.vinsguru.webfluxpatterns.sec07.dto;

public record ProductResponse(
        Long id,
        String category,
        String description,
        Integer price
) {
}
