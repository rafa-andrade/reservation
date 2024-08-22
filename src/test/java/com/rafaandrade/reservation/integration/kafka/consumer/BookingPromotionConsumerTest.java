package com.rafaandrade.reservation.integration.kafka.consumer;

import com.rafaandrade.reservation.integration.kafka.message.ReservationMessage;
import com.rafaandrade.reservation.model.enums.ReservationStatus;
import com.rafaandrade.reservation.service.BookingPromotionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookingPromotionConsumerTest {

    @Mock
    private BookingPromotionService bookingPromotionService;

    @InjectMocks
    private BookingPromotionConsumer bookingPromotionConsumer;

    @Test
    void testProcess() {
        ReservationMessage reservationMessage = new ReservationMessage(
                10L,
                "name",
                "email",
                LocalDate.now(),
                ReservationStatus.REQUESTED
        );

        bookingPromotionConsumer.process(reservationMessage);

        verify(bookingPromotionService, times(1)).promotes(reservationMessage.email(), reservationMessage.date());
    }
}
