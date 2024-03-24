package com.vinsguru.webfluxpatterns.sec06.dto;

import java.time.LocalDate;

public record PromotionResponse(
        Long id,
        String type,
        Double discount,
        LocalDate endDate
) {
}
