package com.vinsguru.webfluxpatterns.sec04.client;

import com.vinsguru.webfluxpatterns.sec04.dto.PaymentRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.PaymentResponse;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private final String DEDUCT = "deduct";
    private final String REFUND = "refund";

    private final WebClient client;

    public UserClient(@Value("${sec04.user.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PaymentResponse> deduct(PaymentRequest paymentRequest) {
        return callUserService(DEDUCT, paymentRequest);
    }

    public Mono<PaymentResponse> refund(PaymentRequest paymentRequest) {
        return callUserService(REFUND, paymentRequest);
    }

    private Mono<PaymentResponse> callUserService(String endpoint, PaymentRequest paymentRequest) {
        return client.post()
                .uri(endpoint)
                .bodyValue(paymentRequest)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(this.buildErrorResponse(paymentRequest));
    }

    private PaymentResponse buildErrorResponse(PaymentRequest request) {
        return new PaymentResponse(
                null,
                request.userId(),
                null,
                request.amount(),
                Status.FAILED
        );

    }

}
