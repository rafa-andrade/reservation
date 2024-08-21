package com.rafaandrade.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rafaandrade.reservation.controller.request.ReservationRequest;
import com.rafaandrade.reservation.controller.request.ReservationResponse;
import com.rafaandrade.reservation.model.Reservation;
import com.rafaandrade.reservation.service.BookingService;
import com.rafaandrade.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@DisabledInAotMode
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private ReservationService reservationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void testBookSuccess() throws Exception {
        UUID externalReference = UUID.randomUUID();
        ReservationRequest request = new ReservationRequest("name", "email@test.com", LocalDate.now());
        Reservation reservation = new Reservation();
        reservation.setExternalReference(externalReference);

        when(bookingService.book(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(externalReference)));

        verify(bookingService, times(1)).book(any(Reservation.class));
    }

    @Test
    void testGetReservationFound() throws Exception {
        UUID externalReference = UUID.randomUUID();
        Reservation reservation = new Reservation();
        ReservationResponse reservationResponse = ReservationResponse.from(reservation);

        when(reservationService.findReservationByReference(externalReference)).thenReturn(Optional.of(reservation));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reservations/{externalReference}", externalReference)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(reservationResponse)));

        verify(reservationService, times(1)).findReservationByReference(externalReference);
    }

    @Test
    void testGetReservationNotFound() throws Exception {
        UUID externalReference = UUID.randomUUID();

        when(reservationService.findReservationByReference(externalReference)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reservations/{externalReference}", externalReference)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(reservationService, times(1)).findReservationByReference(externalReference);
    }

    @Test
    void testGetAllReservations() throws Exception {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);
        List<ReservationResponse> reservationResponses = Arrays.asList(
                ReservationResponse.from(reservation1),
                ReservationResponse.from(reservation2)
        );

        when(reservationService.findAllReservations()).thenReturn(reservations);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reservations")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(reservationResponses)));

        verify(reservationService, times(1)).findAllReservations();
    }
}
