package com.vinsguru.webfluxpatterns.sec05.dto;

import java.time.LocalDate;

public record RoomReservationRequest(

        String city,
        LocalDate checkIn,
        LocalDate checkOut,
        String category

) {
}
