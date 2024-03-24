package com.vinsguru.webfluxpatterns.sec08.dto;

import java.util.List;

public record ProductAggregate(
        Long id,
        String category,
        String description,
        List<Review> reviews
) {
}
