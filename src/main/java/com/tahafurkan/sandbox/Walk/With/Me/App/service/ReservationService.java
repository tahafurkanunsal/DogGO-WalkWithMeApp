package com.tahafurkan.sandbox.Walk.With.Me.App.service;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.ReservationDto;

import java.util.List;

public interface ReservationService {

    List<ReservationDto> getAllReservations();

    ReservationDto getReservationById(Long id);

    ReservationDto updateReservation(Long id, ReservationDto reservationDto);

    void deleteReservation(Long id);

}