package com.vinsguru.webfluxpatterns.sec08.client;

import com.vinsguru.webfluxpatterns.sec08.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private final WebClient client;

    public ProductClient(@Value("${sec08.product.service}") String baseUrl) {
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
                .onErrorResume(ex -> Mono.empty());
    }

}
