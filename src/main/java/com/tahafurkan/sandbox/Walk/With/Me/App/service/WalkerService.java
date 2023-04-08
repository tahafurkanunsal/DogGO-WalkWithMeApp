package com.tahafurkan.sandbox.Walk.With.Me.App.service;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.ReservationDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.WalkerDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.WalkerReservationDetailsDto;

import java.util.List;

public interface WalkerService {

    WalkerDto createWalker(WalkerDto walkerDto);

    WalkerDto getWalkerById(Long id);

    List<WalkerDto> getAllWalkers();

    WalkerDto updateWalker(Long id, WalkerDto walkerDto);

    void deleteWalker(Long id);

    List<WalkerReservationDetailsDto> getWalkerReservations(Long walkerId);

    WalkerReservationDetailsDto getReservationById(Long walkerId, Long reservationId);

    WalkerReservationDetailsDto approveReservation(Long walkerId, Long reservationId);

    ReservationDto startReservation(Long walkerId, Long reservationId);

    ReservationDto finishReservation(Long walkerId, Long reservationId);

}