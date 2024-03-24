package com.vinsguru.webfluxpatterns.sec06.dto;

public record Review(

        Long id,
        String user,
        Integer rating,
        String comment
) {
}
