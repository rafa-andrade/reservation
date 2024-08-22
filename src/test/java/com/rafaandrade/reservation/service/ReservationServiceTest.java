package com.rafaandrade.reservation.service;

import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void testFindReservationByReferenceFound() {
        UUID reference = UUID.randomUUID();
        Reservation reservation = new Reservation();
        when(reservationRepository.findByExternalReference(reference)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.findReservationByReference(reference);

        assertTrue(result.isPresent());
        assertEquals(reservation, result.get());
        verify(reservationRepository, times(1)).findByExternalReference(reference);
    }

    @Test
    void testFindReservationByReferenceNotFound() {
        UUID reference = UUID.randomUUID();
        when(reservationRepository.findByExternalReference(reference)).thenReturn(Optional.empty());

        Optional<Reservation> result = reservationService.findReservationByReference(reference);

        assertFalse(result.isPresent());
        verify(reservationRepository, times(1)).findByExternalReference(reference);
    }

    @Test
    void testFindAllReservations() {
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findAll()).thenReturn(reservations);

        List<Reservation> result = reservationService.findReservations(Optional.empty());

        assertEquals(2, result.size());
        assertEquals(reservations, result);
        verify(reservationRepository, times(1)).findAll();
    }
}
