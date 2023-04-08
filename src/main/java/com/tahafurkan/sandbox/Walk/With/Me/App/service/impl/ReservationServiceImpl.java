package com.tahafurkan.sandbox.Walk.With.Me.App.service.impl;


import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Reservation;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.ReservationDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.ResourceNotFoundException;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.ReservationRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ModelMapper modelMapper;



    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservationList = reservationRepository.findAll();

        return reservationList.stream().map(reservation -> modelMapper
                .map(reservation, ReservationDto.class)).collect(Collectors.toList());
    }


    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));

        return modelMapper.map(reservation, ReservationDto.class);
    }


    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {

        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));


        existingReservation.setReservationStartTime(reservationDto.getReservationStartTime());
        existingReservation.setReservationEndTime(reservationDto.getReservationEndTime());
        existingReservation.setReservationDate(reservationDto.getReservationDate());
        existingReservation.setReservationStatus(reservationDto.getReservationStatus());


        Reservation updatedReservation = reservationRepository.save(existingReservation);


        return modelMapper.map(updatedReservation, ReservationDto.class);
    }


    public void deleteReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));


        reservationRepository.delete(reservation);
    }
}