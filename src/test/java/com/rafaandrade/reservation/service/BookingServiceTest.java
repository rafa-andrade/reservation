package com.rafaandrade.reservation.service;

import com.rafaandrade.reservation.integration.kafka.producer.BookingPromotionProducer;
import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.model.enums.ReservationStatus;
import com.rafaandrade.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private BookingPromotionProducer bookingPromotionProducer;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testBookSuccess() {
        Reservation reservation = new Reservation();
        reservation.setStatus(ReservationStatus.REQUESTED);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = bookingService.book(reservation);

        assertEquals(ReservationStatus.REQUESTED, result.getStatus());
        verify(reservationRepository, times(1)).save(reservation);
        verify(bookingPromotionProducer, times(1)).send(reservation);
    }
}
