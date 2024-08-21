package com.rafaandrade.reservation.integration.kafka.producer;

import com.rafaandrade.reservation.integration.kafka.message.ReservationMessage;
import com.rafaandrade.reservation.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingPromotionProducerTest {

    @Mock
    private KafkaTemplate<String, ReservationMessage> kafkaTemplate;

    @InjectMocks
    private BookingPromotionProducer bookingPromotionProducer;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(bookingPromotionProducer, "bookingPromotionTopic", "test-booking-promotion-topic");
    }

    @Test
    void testSendSuccess() {
        Reservation reservation = new Reservation();
        reservation.setDate(LocalDate.parse("2024-08-20"));

        ReservationMessage reservationMessage = ReservationMessage.from(reservation);

        bookingPromotionProducer.send(reservation);

        verify(kafkaTemplate, times(1)).send("test-booking-promotion-topic", "2024-08-20", reservationMessage);
    }
}
