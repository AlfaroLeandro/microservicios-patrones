package com.vinsguru.webfluxpatterns.sec03.dto;

public record Address(
        String street,
        String city,
        String state,
        String zipCode
) {
}
