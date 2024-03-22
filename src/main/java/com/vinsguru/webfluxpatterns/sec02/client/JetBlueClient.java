package com.vinsguru.webfluxpatterns.sec02.client;

import com.vinsguru.webfluxpatterns.sec02.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JetBlueClient {

    private final String JETBLUE = "JETBLUE";
    private final WebClient webClient;

    public JetBlueClient(@Value("${sec02.jetblue.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return this.webClient
                .get()
                .uri("{from}/{to}", from, to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .map(flightResult -> normalize(flightResult, from, to))
                .onErrorResume(ex -> Mono.empty());
    }

    private FlightResult normalize(FlightResult result, String from, String to) {
        return new FlightResult(
                JETBLUE,
                from,
                to,
                result.price(),
                result.date()
        );
    }
}
