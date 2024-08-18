package com.rafaandrade.reservation.service;

import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Optional<Reservation> findReservationByReference(UUID externalReference) {
        return reservationRepository.findByExternalReference(externalReference);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
}