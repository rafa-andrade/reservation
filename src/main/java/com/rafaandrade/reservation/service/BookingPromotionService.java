package com.rafaandrade.reservation.service;

import com.rafaandrade.reservation.integration.kafka.producer.StatusNotificationProducer;
import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.model.enums.ReservationStatus;
import com.rafaandrade.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.rafaandrade.reservation.model.enums.ReservationStatus.ACCEPTED;
import static com.rafaandrade.reservation.model.enums.ReservationStatus.REJECTED;

@Service
@RequiredArgsConstructor
public class BookingPromotionService {

    public static final long MAX_BOOKING_PER_DAY = 10;

    private final ReservationRepository reservationRepository;
    private final StatusNotificationProducer statusNotificationProducer;

    @Transactional
    public void promotes(String email, LocalDate date) {
        Optional<Reservation> acceptedReservation = reservationRepository.findByEmailAndDateAndStatus(email, date, ACCEPTED);
        if (acceptedReservation.isPresent()) {
            return;
        }

        long acceptedCount = reservationRepository.countByStatusAndDate(ACCEPTED, date);
        ReservationStatus status = (acceptedCount < MAX_BOOKING_PER_DAY) ? ACCEPTED : REJECTED;
        reservationRepository.updateStatusByEmailAndDate(status, email, date);

        statusNotificationProducer.send(email, date, status);
    }

}
