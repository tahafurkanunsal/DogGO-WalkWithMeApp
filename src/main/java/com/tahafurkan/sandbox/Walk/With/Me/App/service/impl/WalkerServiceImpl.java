package com.tahafurkan.sandbox.Walk.With.Me.App.service.impl;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Reservation;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Walker;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.ReservationDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.WalkerDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.WalkerReservationDetailsDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.enums.ReservationStatus;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.BadRequestException;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.ReservationNotFinishableException;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.ReservationNotStartableException;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.ResourceNotFoundException;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.ReservationRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.WalkerRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.WalkerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalkerServiceImpl implements WalkerService {

    @Autowired
    WalkerRepository walkerRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public WalkerDto createWalker(WalkerDto walkerDto) {

        Walker walker = modelMapper.map(walkerDto, Walker.class);

        Walker savedWalker = walkerRepository.save(walker);

        return modelMapper.map(savedWalker, WalkerDto.class);
    }

    @Override
    public WalkerDto getWalkerById(Long id) {
        Walker walker = walkerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Walker", "id", id));

        return modelMapper.map(walker, WalkerDto.class);

    }

    @Override
    public List<WalkerDto> getAllWalkers() {

        List<Walker> walkerList = walkerRepository.findAll();

        return walkerList.stream().map(walker -> modelMapper.map(walker, WalkerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public WalkerDto updateWalker(Long id, WalkerDto walkerDto) {

        Walker existingWalker = walkerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Walker", "id", id));

        existingWalker.setFirstName(walkerDto.getFirstName());
        existingWalker.setLastName(walkerDto.getLastName());
        existingWalker.setEmail(walkerDto.getEmail());
        existingWalker.setPassword(walkerDto.getPassword());

        Walker updatedWalker = walkerRepository.save(existingWalker);

        return modelMapper.map(updatedWalker, WalkerDto.class);
    }

    @Override
    public void deleteWalker(Long id) {
        Walker walker = walkerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Walker", "id", id));

        walkerRepository.delete(walker);
    }

    @Override
    public List<WalkerReservationDetailsDto> getWalkerReservations(Long walkerId) {

        Walker walker = walkerRepository.findById(walkerId)
                .orElseThrow(() -> new ResourceNotFoundException("Walker", "id", walkerId));

        List<Reservation> reservations = walker.getReservations();
        List<WalkerReservationDetailsDto> walkerReservationDetailsDtos = new ArrayList<>();

        for (Reservation reservation : reservations) {
            WalkerReservationDetailsDto walkerReservationDetailsDto = new WalkerReservationDetailsDto();
            walkerReservationDetailsDto.setWalkerId(walkerId);
            walkerReservationDetailsDto.setWalkerName(walker.getFirstName() + " " + walker.getLastName());
            walkerReservationDetailsDto.setReservationDtos(Collections.singletonList(modelMapper.map(reservation, ReservationDto.class)));
            walkerReservationDetailsDtos.add(walkerReservationDetailsDto);
        }

        return walkerReservationDetailsDtos;
    }

    @Override
    public WalkerReservationDetailsDto approveReservation(Long walkerId, Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservationId));

        Walker walker = walkerRepository.findById(walkerId)
                .orElseThrow(() -> new ResourceNotFoundException("Walker", "id", walkerId));

        reservation.setWalker(walker);
        reservation.setReservationStatus(ReservationStatus.APPROVED);
        Reservation updatedReservation = reservationRepository.save(reservation);

        WalkerReservationDetailsDto detailsDto = new WalkerReservationDetailsDto();
        detailsDto.setWalkerId(walkerId);
        detailsDto.setWalkerName(walker.getFirstName() + " " + walker.getLastName());

        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation r : walker.getReservations()) {
            reservationDtos.add(modelMapper.map(r, ReservationDto.class));
        }
        detailsDto.setReservationDtos(reservationDtos);

        return detailsDto;
    }


    @Override
    public WalkerReservationDetailsDto getReservationById(Long walkerId, Long reservationId) {

        Walker walker = walkerRepository.findById(walkerId)
                .orElseThrow(() -> new ResourceNotFoundException("Walker", "id", walkerId));

        Reservation reservation = walker.getReservations().stream()
                .filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservationId));

        WalkerReservationDetailsDto walkerReservationDetailsDto = new WalkerReservationDetailsDto();
        walkerReservationDetailsDto.setWalkerId(walkerId);
        walkerReservationDetailsDto.setWalkerName(walker.getFirstName() + " " + walker.getLastName());

        ReservationDto reservationDto = modelMapper.map(reservation, ReservationDto.class);

        walkerReservationDetailsDto.setReservationDtos(Collections.singletonList(reservationDto));

        return walkerReservationDetailsDto;
    }

    @Override
    public ReservationDto startReservation(Long walkerId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservationId));

        if (reservation.getWalker() != null && reservation.getReservationStatus() == ReservationStatus.STARTED) {
            throw new BadRequestException("Reservation is already started");
        }

        if (reservation.getReservationStatus() != ReservationStatus.APPROVED) {
            throw new ReservationNotStartableException("Reservation cannot be started yet");
        }

        Walker walker = walkerRepository.findById(walkerId)
                .orElseThrow(() -> new ResourceNotFoundException("Walker", "id", walkerId));

        reservation.setWalker(walker);
        reservation.setReservationStartTime(LocalDateTime.now());
        reservation.setReservationStatus(ReservationStatus.STARTED);
        reservation.setReservationDate(LocalDate.now());

        Reservation updatedReservation = reservationRepository.save(reservation);

        return modelMapper.map(updatedReservation, ReservationDto.class);
    }

    @Override
    public ReservationDto finishReservation(Long walkerId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservationId));

        if (reservation.getWalker() == null || !reservation.getWalker().getId().equals(walkerId)) {
            throw new BadRequestException("Walker is not assigned to this reservation");
        }

        if (reservation.getReservationStatus() != ReservationStatus.STARTED) {
            throw new ReservationNotFinishableException("Reservation cannot be finished yet");
        }

        reservation.setReservationDate(LocalDate.now());
        reservation.setReservationEndTime(LocalDateTime.now());
        reservation.setReservationStatus(ReservationStatus.FINISHED);

        Reservation updatedReservation = reservationRepository.save(reservation);

        return modelMapper.map(updatedReservation, ReservationDto.class);
    }

    private boolean isReservationStartable(Reservation reservation) {
        LocalDateTime startTime = reservation.getReservationStartTime();
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime.minusMinutes(5));
    }
}