package com.rafaandrade.reservation.integration.kafka.message;

import com.rafaandrade.reservation.model.enums.ReservationStatus;

import java.time.LocalDate;

public record StatusNotificationMessage(
        String email,
        LocalDate date,
        ReservationStatus status
) {
}
