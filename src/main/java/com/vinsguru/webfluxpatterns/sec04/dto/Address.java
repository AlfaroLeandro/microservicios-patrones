package com.vinsguru.webfluxpatterns.sec04.dto;

public record Address(
        String street,
        String city,
        String state,
        String zipCode
) {
}
