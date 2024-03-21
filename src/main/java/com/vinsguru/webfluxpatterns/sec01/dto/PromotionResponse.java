package com.vinsguru.webfluxpatterns.sec01.dto;

import java.time.LocalDate;

public record PromotionResponse(
        Long id,
        String type,
        Double discount,
        LocalDate endDate
) {
}
