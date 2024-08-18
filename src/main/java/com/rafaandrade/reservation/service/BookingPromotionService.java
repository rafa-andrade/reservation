package com.rafaandrade.reservation.service;

import com.rafaandrade.reservation.integration.kafka.producer.StatusNotificationProducer;
import com.rafaandrade.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rafaandrade.reservation.model.enums.ReservationStatus.ACCEPTED;
import static com.rafaandrade.reservation.model.enums.ReservationStatus.REJECTED;

@Service
@RequiredArgsConstructor
public class BookingPromotionService {

    public static final long MAX_BOOKING_PER_DAY = 100;

    private final ReservationRepository reservationRepository;
    private final StatusNotificationProducer statusNotificationProducer;

    @Transactional
    public void promotesBooking(long reservationId) {
        reservationRepository.findById(reservationId)
                .ifPresent(reservation -> {
                    long acceptedCount = reservationRepository.countByStatusAndDate(ACCEPTED, reservation.getDate());
                    if (acceptedCount < MAX_BOOKING_PER_DAY) {
                        reservation.setStatus(ACCEPTED);
                    } else {
                        reservation.setStatus(REJECTED);
                    }
                    statusNotificationProducer.send(reservation);
                });
    }

}
