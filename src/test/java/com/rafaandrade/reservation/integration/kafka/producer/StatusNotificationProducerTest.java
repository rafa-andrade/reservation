package com.rafaandrade.reservation.integration.kafka.producer;

import com.rafaandrade.reservation.integration.kafka.message.ReservationMessage;
import com.rafaandrade.reservation.integration.kafka.message.StatusNotificationMessage;
import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.model.enums.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StatusNotificationProducerTest {

    @Mock
    private KafkaTemplate<String, StatusNotificationMessage> kafkaTemplate;

    @InjectMocks
    private StatusNotificationProducer statusNotificationProducer;

    @Test
    void testSendSuccess() {
        String email = "email@test.com";
        LocalDate date = LocalDate.now();
        ReservationStatus status = ReservationStatus.ACCEPTED;

        statusNotificationProducer.send(email, date, status);

        verify(kafkaTemplate, times(1)).send(eq("status-notification"), any(StatusNotificationMessage.class));
    }
}
