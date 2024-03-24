package com.vinsguru.webfluxpatterns.sec05.service;

import com.vinsguru.webfluxpatterns.sec05.client.RoomClient;
import com.vinsguru.webfluxpatterns.sec05.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class RoomReservationHandler extends ReservationHandler{
    @Autowired
    private RoomClient roomClient;

    @Override
    protected ReservationType getType() {
        return ReservationType.ROOM;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.map(this::toRoomRequest)
                .transform(this.roomClient::reserve) // = a map pero en lugar de ir de obj a obj, va de flux a flux
                .map(this::toResponse);

    }

    private RoomReservationRequest toRoomRequest(ReservationItemRequest request) {
        return new RoomReservationRequest(
                request.city(),
                request.from(),
                request.to(),
                request.category()
        );
    }

    private ReservationItemResponse toResponse(RoomReservationResponse response) {
        return new ReservationItemResponse(
                response.reservationId(),
                this.getType(),
                response.category(),
                response.city(),
                response.checkIn(),
                response.checkOut(),
                response.price()
        );
    }
}
