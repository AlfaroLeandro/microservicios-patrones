package com.vinsguru.webfluxpatterns.sec07.dto;

public record Review(

        Long id,
        String user,
        Integer rating,
        String comment
) {
}
