package com.rafaandrade.reservation.repository;

import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    long countByStatusAndDate(ReservationStatus status, LocalDate date);

    Optional<Reservation> findByExternalReference(UUID externalReference);
}