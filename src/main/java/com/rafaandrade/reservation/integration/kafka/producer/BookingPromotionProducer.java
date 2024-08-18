package com.rafaandrade.reservation.integration.kafka.producer;

import com.rafaandrade.reservation.integration.kafka.message.ReservationMessage;
import com.rafaandrade.reservation.model.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Setter
@RequiredArgsConstructor
public class BookingPromotionProducer {

    private final KafkaTemplate<String, ReservationMessage> kafkaTemplate;

    @Value("${spring.kafka.topics.booking-promotion.name}")
    private String bookingPromotionTopic;

    public void send(Reservation reservation) {
        kafkaTemplate.send(
                bookingPromotionTopic,
                reservation.getDate().toString(),
                ReservationMessage.from(reservation)
        );
    }
}
