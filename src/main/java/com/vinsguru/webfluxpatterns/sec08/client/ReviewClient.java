package com.vinsguru.webfluxpatterns.sec08.client;

import com.vinsguru.webfluxpatterns.sec08.dto.Review;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${sec08.review.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }


    @CircuitBreaker(name = "review-service", fallbackMethod = "fallBackReview")
    public Mono<List<Review>> getReviews(Long id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(s -> s.is4xxClientError(), _ -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
                .retry(5)
//                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .timeout(Duration.ofMillis(300));
//                .onErrorReturn(new ArrayList<>()); -> si se captura aca la exception el circuit breaker no la ve
    }

    public Mono<List<Review>> fallBackReview(Long id, Throwable ex) {
        System.out.println("fallback reviews called : " + ex.getMessage());
        return Mono.just(new ArrayList<>());
    }

}
