package com.rafaandrade.reservation.service;

import com.rafaandrade.reservation.integration.kafka.producer.BookingPromotionProducer;
import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ReservationRepository reservationRepository;
    private final BookingPromotionProducer bookingPromotionProducer;

    public Reservation book(Reservation reservation) {
        reservationRepository.saveAndFlush(reservation);
        bookingPromotionProducer.send(reservation);
        return reservation;
    }
}