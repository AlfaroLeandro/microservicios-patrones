package com.vinsguru.webfluxpatterns.sec07.dto;

import java.util.List;

public record ProductAggregate(
        Long id,
        String category,
        String description,
        List<Review> reviews
) {
}
