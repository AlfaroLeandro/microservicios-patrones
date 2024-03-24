package com.vinsguru.webfluxpatterns.sec04.client;

import com.vinsguru.webfluxpatterns.sec04.dto.ShippingRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.ShippingResponse;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ShippingClient {
    private final String SCHEDULE = "schedule";
    private final String CANCEL = "cancel";

    private final WebClient client;

    public ShippingClient(@Value("${sec04.shipping.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest request) {
        return callShippingService(SCHEDULE, request);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest request) {
        return callShippingService(CANCEL, request);
    }

    private Mono<ShippingResponse> callShippingService(String endpoint, ShippingRequest request) {
        return client.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private ShippingResponse buildErrorResponse(ShippingRequest request) {
        return new ShippingResponse(
                null,
                request.quantity(),
                Status.FAILED,
                null,
                null
        );

    }

}
