package com.rafaandrade.reservation.controller.request;

import com.rafaandrade.reservation.model.Reservation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

public record ReservationRequest(
    @NotBlank
    String name,

    @NotBlank
    @Email
    String email,

    @NotNull
    LocalDate date
) {
    public Reservation toModel() {
        Reservation reservation = new Reservation();
        reservation.setName(name);
        reservation.setEmail(email);
        reservation.setDate(date);
        reservation.setExternalReference(randomUUID());
        reservation.setCreationDate(now());
        return reservation;
    }
}