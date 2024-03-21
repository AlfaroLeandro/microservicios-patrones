package com.vinsguru.webfluxpatterns.sec01.dto;

import java.util.List;

public record ProductAggregate(
        Long id,
        String category,
        String description,
        Price price,
        List<Review> reviews
) {
}
