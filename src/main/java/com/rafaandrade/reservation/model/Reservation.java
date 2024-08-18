package com.rafaandrade.reservation.model;

import com.rafaandrade.reservation.model.enums.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private LocalDate date;

    private ReservationStatus status;

    @Column(unique = true)
    private UUID externalReference;

    private LocalDateTime creationDate;
}