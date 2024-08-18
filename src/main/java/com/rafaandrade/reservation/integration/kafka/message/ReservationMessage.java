package com.rafaandrade.reservation.integration.kafka.message;

import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.model.enums.ReservationStatus;

import java.time.LocalDate;

public record ReservationMessage (
    Long id,
    String name,
    String email,
    LocalDate date,
    ReservationStatus status
) {
    public static ReservationMessage from(Reservation reservation) {
        return new ReservationMessage(
                reservation.getId(),
                reservation.getName(),
                reservation.getEmail(),
                reservation.getDate(),
                reservation.getStatus()
        );
    }
}
