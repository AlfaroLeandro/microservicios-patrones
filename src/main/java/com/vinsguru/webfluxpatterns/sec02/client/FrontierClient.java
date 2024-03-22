package com.vinsguru.webfluxpatterns.sec02.client;

import com.vinsguru.webfluxpatterns.sec02.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FrontierClient {
    private final WebClient webClient;

    public FrontierClient(@Value("${sec02.frontier.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return this.webClient
                .post()
                .bodyValue(new FrontierRequest(from, to))
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(ex -> Mono.empty());
    }

    private record FrontierRequest (
            String from,
            String to
    ) {}
}
