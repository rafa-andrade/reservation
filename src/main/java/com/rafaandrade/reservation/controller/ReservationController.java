package com.rafaandrade.reservation.controller;

import com.rafaandrade.reservation.controller.request.ReservationRequest;
import com.rafaandrade.reservation.controller.request.ReservationResponse;
import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.service.BookingService;
import com.rafaandrade.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final BookingService bookingService;
    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(CREATED)
    public UUID book(@RequestBody @Valid ReservationRequest request) {
        Reservation reservation = bookingService.book(request.toModel());
        return reservation.getExternalReference();
    }

    @GetMapping("/{externalReference}")
    public ReservationResponse getReservation(@PathVariable UUID externalReference) {
        return reservationService.findReservationByReference(externalReference)
                .map(ReservationResponse::from)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping
    public List<ReservationResponse> getReservations(@RequestParam(required = false) LocalDate date) {
        return reservationService.findReservations(ofNullable(date))
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }
}