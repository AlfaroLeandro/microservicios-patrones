package com.vinsguru.webfluxpatterns.sec08.dto;

public record ProductResponse(
        Long id,
        String category,
        String description,
        Integer price
) {
}
