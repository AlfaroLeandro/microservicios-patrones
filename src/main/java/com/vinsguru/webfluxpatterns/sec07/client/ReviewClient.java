package com.vinsguru.webfluxpatterns.sec07.client;

import com.vinsguru.webfluxpatterns.sec07.dto.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${sec07.review.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

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
                .timeout(Duration.ofMillis(300)) //agre
                .onErrorReturn(new ArrayList<>());
    }


}
