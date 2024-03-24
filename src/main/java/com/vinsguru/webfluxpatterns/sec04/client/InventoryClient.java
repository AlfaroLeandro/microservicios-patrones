package com.vinsguru.webfluxpatterns.sec04.client;

import com.vinsguru.webfluxpatterns.sec04.dto.InventoryRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.InventoryResponse;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventoryClient {
    private final String DEDUCT = "deduct";
    private final String RESTORE = "restore";

    private final WebClient client;

    public InventoryClient(@Value("${sec04.inventory.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return callInventoryService(DEDUCT, request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return callInventoryService(RESTORE, request);
    }

    private Mono<InventoryResponse> callInventoryService(String endpoint, InventoryRequest request) {
        return client.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private InventoryResponse buildErrorResponse(InventoryRequest request) {
        return new InventoryResponse(
                null,
                request.productId(),
                request.quantity(),
                null,
                Status.FAILED
        );

    }

}
