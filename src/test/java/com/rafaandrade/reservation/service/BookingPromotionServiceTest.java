package com.rafaandrade.reservation.service;

import com.rafaandrade.reservation.integration.kafka.producer.StatusNotificationProducer;
import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.model.enums.ReservationStatus;
import com.rafaandrade.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingPromotionServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private StatusNotificationProducer statusNotificationProducer;

    @InjectMocks
    private BookingPromotionService bookingPromotionService;

    @Test
    void testPromotesAccepted() {
        long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setDate(LocalDate.parse("2024-08-20"));

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.countByStatusAndDate(ReservationStatus.ACCEPTED, reservation.getDate())).thenReturn(30L);

        bookingPromotionService.promotes(reservationId);

        assertEquals(ReservationStatus.ACCEPTED, reservation.getStatus());
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, times(1)).countByStatusAndDate(ReservationStatus.ACCEPTED, reservation.getDate());
        verify(statusNotificationProducer, times(1)).send(reservation);
    }

    @Test
    void testPromotesRejected() {
        long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setDate(LocalDate.parse("2024-08-20"));

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.countByStatusAndDate(ReservationStatus.ACCEPTED, reservation.getDate())).thenReturn(60L);

        bookingPromotionService.promotes(reservationId);

        assertEquals(ReservationStatus.REJECTED, reservation.getStatus());
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, times(1)).countByStatusAndDate(ReservationStatus.ACCEPTED, reservation.getDate());
        verify(statusNotificationProducer, times(1)).send(reservation);
    }

    @Test
    void testPromotesReservationNotFound() {
        long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        bookingPromotionService.promotes(reservationId);

        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, never()).countByStatusAndDate(any(), any());
        verify(statusNotificationProducer, never()).send(any());
    }
}
