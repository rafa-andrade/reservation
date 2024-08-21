package com.rafaandrade.reservation.integration.kafka.producer;

import com.rafaandrade.reservation.integration.kafka.message.ReservationMessage;
import com.rafaandrade.reservation.model.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StatusNotificationProducerTest {

    @Mock
    private KafkaTemplate<String, ReservationMessage> kafkaTemplate;

    @InjectMocks
    private StatusNotificationProducer statusNotificationProducer;

    @Test
    void testSendSuccess() {
        Reservation reservation = new Reservation();
        ReservationMessage reservationMessage = ReservationMessage.from(reservation);

        statusNotificationProducer.send(reservation);

        verify(kafkaTemplate, times(1)).send("status-notification", reservationMessage);
    }
}
