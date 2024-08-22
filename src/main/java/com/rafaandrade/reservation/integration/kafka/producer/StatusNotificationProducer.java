package com.rafaandrade.reservation.integration.kafka.producer;

import com.rafaandrade.reservation.integration.kafka.message.StatusNotificationMessage;
import com.rafaandrade.reservation.model.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class StatusNotificationProducer {

    private final KafkaTemplate<String, StatusNotificationMessage> kafkaTemplate;

    public void send(String email, LocalDate date, ReservationStatus status) {
        kafkaTemplate.send("status-notification", new StatusNotificationMessage(email, date, status));
    }
}
