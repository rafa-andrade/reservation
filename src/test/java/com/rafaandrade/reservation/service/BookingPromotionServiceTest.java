package com.rafaandrade.reservation.service;

import com.rafaandrade.reservation.integration.kafka.producer.StatusNotificationProducer;
import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.rafaandrade.reservation.model.enums.ReservationStatus.ACCEPTED;
import static com.rafaandrade.reservation.model.enums.ReservationStatus.REJECTED;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingPromotionServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private StatusNotificationProducer statusNotificationProducer;

    @InjectMocks
    private BookingPromotionService bookingPromotionService;

    private String email;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        email = "test@example.com";
        date = LocalDate.now();
    }

    @Test
    void testPromotes_AlreadyAcceptedReservation() {
        when(reservationRepository.findByEmailAndDateAndStatus(email, date, ACCEPTED))
                .thenReturn(Optional.of(new Reservation()));

        bookingPromotionService.promotes(email, date);

        verify(reservationRepository, never()).countByStatusAndDate(any(), any());
        verify(reservationRepository, never()).updateStatusByEmailAndDate(any(), any(), any());
        verify(statusNotificationProducer, never()).send(anyString(), any(), any());
    }

    @Test
    void testPromotes_LessThanMaxBookings() {
        when(reservationRepository.findByEmailAndDateAndStatus(email, date, ACCEPTED))
                .thenReturn(Optional.empty());

        when(reservationRepository.countByStatusAndDate(ACCEPTED, date))
                .thenReturn(5L);

        bookingPromotionService.promotes(email, date);

        verify(reservationRepository, times(1))
                .updateStatusByEmailAndDate(ACCEPTED, email, date);

        verify(statusNotificationProducer, times(1))
                .send(email, date, ACCEPTED);
    }

    @Test
    void testPromotes_ExceedsMaxBookings() {
        when(reservationRepository.findByEmailAndDateAndStatus(email, date, ACCEPTED))
                .thenReturn(Optional.empty());

        when(reservationRepository.countByStatusAndDate(ACCEPTED, date))
                .thenReturn(60L);

        bookingPromotionService.promotes(email, date);

        verify(reservationRepository, times(1))
                .updateStatusByEmailAndDate(REJECTED, email, date);

        verify(statusNotificationProducer, times(1))
                .send(email, date, REJECTED);
    }
}
