package com.vinsguru.webfluxpatterns.sec01.dto;

public record Review(

        Long id,
        String user,
        Integer rating,
        String comment
) {
}
