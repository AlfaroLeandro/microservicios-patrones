package com.vinsguru.webfluxpatterns.sec06.client;

import com.vinsguru.webfluxpatterns.sec06.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductClient {

    private final WebClient client;

    public ProductClient(@Value("${sec06.product.service}") String baseUrl) {
        this.client = WebClient.builder()
                               .baseUrl(baseUrl)
                               .build();
    }

    public Mono<ProductResponse> getProduct(Long id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

}
