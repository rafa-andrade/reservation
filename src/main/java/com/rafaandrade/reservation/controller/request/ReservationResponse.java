package com.rafaandrade.reservation.controller.request;

import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.model.enums.ReservationStatus;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationResponse (
    String name,
    String email,
    LocalDate date,
    ReservationStatus status,
    UUID externalReference
){
    public static ReservationResponse from (Reservation reservation){
        return new ReservationResponse(
                reservation.getName(),
                reservation.getEmail(),
                reservation.getDate(),
                reservation.getStatus(),
                reservation.getExternalReference()
        );
    }
}