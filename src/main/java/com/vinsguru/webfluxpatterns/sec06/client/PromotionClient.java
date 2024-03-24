package com.vinsguru.webfluxpatterns.sec06.client;

import com.vinsguru.webfluxpatterns.sec06.dto.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PromotionClient {
    private final WebClient client;
    private final PromotionResponse noPromotion = new PromotionResponse(-1L, "no promotion", 0.0, LocalDate.now());
    public PromotionClient(@Value("${sec01.promotion.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PromotionResponse> getPromotion(Long id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(PromotionResponse.class)
                .onErrorReturn(noPromotion);
    }
}
