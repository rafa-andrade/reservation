package com.rafaandrade.reservation.repository;

import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    long countByStatusAndDate(ReservationStatus status, LocalDate date);

    Optional<Reservation> findByExternalReference(UUID externalReference);

    Optional<Reservation> findByEmailAndDateAndStatus(String email, LocalDate date, ReservationStatus status);

    @Modifying(clearAutomatically = true)
    @Query("update Reservation r set r.status = :status WHERE r.email = :email AND r.date = :date")
    void updateStatusByEmailAndDate(@Param("status") ReservationStatus status, @Param("email") String email, @Param("date") LocalDate date);
}