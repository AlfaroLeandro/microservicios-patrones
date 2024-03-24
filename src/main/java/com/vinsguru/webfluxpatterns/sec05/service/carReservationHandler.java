package com.vinsguru.webfluxpatterns.sec05.service;

import com.vinsguru.webfluxpatterns.sec05.client.CarClient;
import com.vinsguru.webfluxpatterns.sec05.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class carReservationHandler extends ReservationHandler{

    @Autowired
    private CarClient carClient;

    @Override
    protected ReservationType getType() {
        return ReservationType.CAR;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.map(this::toCarRequest)
                .transform(this.carClient::reserve) // = a map pero en lugar de ir de obj a obj, va de flux a flux
                .map(this::toResponse);

    }

    private CarReservationRequest toCarRequest(ReservationItemRequest request) {
        return new CarReservationRequest(
            request.city(),
            request.from(),
            request.to(),
            request.category()
        );
    }

    private ReservationItemResponse toResponse(CarReservationResponse response) {
        return new ReservationItemResponse(
          response.reservationId(),
          this.getType(),
          response.category(),
          response.city(),
          response.pickup(),
          response.drop(),
          response.price()
        );
    }
}