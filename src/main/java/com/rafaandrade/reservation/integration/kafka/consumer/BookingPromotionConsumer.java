package com.rafaandrade.reservation.integration.kafka.consumer;

import com.rafaandrade.reservation.integration.kafka.message.ReservationMessage;
import com.rafaandrade.reservation.service.BookingPromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingPromotionConsumer {

    private final BookingPromotionService bookingPromotionService;

    @KafkaListener(
            topics = "${spring.kafka.topics.booking-promotion.name}",
            groupId = "booking-promotion"
    )
    public void process(ReservationMessage reservation) {
        bookingPromotionService.promotes(reservation.email(), reservation.date());
    }
}