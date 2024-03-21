package com.vinsguru.webfluxpatterns.sec01.dto;

import java.time.LocalDate;

public record Price(
        Integer listPrice,
        Double discount,
        Double discountedPrice,
        Double amountSaved,
        LocalDate endDate
) {
}
