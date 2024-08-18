package com.rafaandrade.reservation.integration.kafka.producer;

import com.rafaandrade.reservation.integration.kafka.message.ReservationMessage;
import com.rafaandrade.reservation.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusNotificationProducer {

    private final KafkaTemplate<String, ReservationMessage> kafkaTemplate;

    public void send(Reservation reservation) {
        kafkaTemplate.send("status-notification", ReservationMessage.from(reservation));
    }
}
