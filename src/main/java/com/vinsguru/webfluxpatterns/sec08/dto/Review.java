package com.vinsguru.webfluxpatterns.sec08.dto;

public record Review(

        Long id,
        String user,
        Integer rating,
        String comment
) {
}
