package com.tahafurkan.sandbox.Walk.With.Me.App.controller;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.ReservationDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.WalkerDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.WalkerReservationDetailsDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.WalkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/walkers")
public class WalkerController {

    @Autowired
    WalkerService walkerService;

    @PostMapping
    public ResponseEntity<WalkerDto> create(@RequestBody WalkerDto walkerDto) {

        WalkerDto savedWalker = walkerService.createWalker(walkerDto);

        return new ResponseEntity<>(savedWalker, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<WalkerDto> getWalker(@PathVariable("id") Long id) {

        WalkerDto walkerDto = walkerService.getWalkerById(id);
        return ResponseEntity.ok(walkerDto);
    }

    @GetMapping
    public ResponseEntity<List<WalkerDto>> getAll() {
        return ResponseEntity.ok(walkerService.getAllWalkers());
    }

    @PutMapping("{walkerId}")
    public ResponseEntity<WalkerDto> update(@PathVariable("walkerId") Long walkerId,
                                            @RequestBody WalkerDto walkerDto) {

        WalkerDto updatedWalker = walkerService.updateWalker(walkerId, walkerDto);
        return ResponseEntity.ok(updatedWalker);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        walkerService.deleteWalker(id);
        return ResponseEntity.ok("Walker deleted successfully");
    }

    @PutMapping("/{walkerId}/approve/{reservationId}")
    public ResponseEntity<WalkerReservationDetailsDto> approveReservation(@PathVariable("walkerId") Long walkerId,
                                                                          @PathVariable("reservationId") Long reservationId) {
        WalkerReservationDetailsDto walkerReservationDetailsDto = walkerService.approveReservation(walkerId, reservationId);
        return ResponseEntity.ok(walkerReservationDetailsDto);
    }

    @GetMapping("{walkerId}/reservations")
    public ResponseEntity<List<WalkerReservationDetailsDto>> getWalkerReservation(@PathVariable("walkerId") Long walkerId) {
        List<WalkerReservationDetailsDto> walkerReservationDetailsDtos = walkerService.getWalkerReservations(walkerId);
        return ResponseEntity.ok(walkerReservationDetailsDtos);
    }

    @GetMapping("{walkerId}/reservations/{reservationId}")
    public ResponseEntity<WalkerReservationDetailsDto> getWalkerReservation(@PathVariable("walkerId") Long walkerId,
                                                                            @PathVariable("reservationId") Long reservationId) {
        WalkerReservationDetailsDto walkerReservationDetailsDtos = walkerService.getReservationById(walkerId, reservationId);
        return ResponseEntity.ok(walkerReservationDetailsDtos);
    }

    @PutMapping("/{walkerId}/{reservationId}/start")
    public ResponseEntity<ReservationDto> startReservation(@PathVariable("walkerId") Long walkerId,
                                                           @PathVariable("reservationId") Long reservationId) {
        ReservationDto reservationDto = walkerService.startReservation(walkerId, reservationId);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @PutMapping("/{walkerId}/{reservationId}/finish")
    public ResponseEntity<ReservationDto> finishReservation(@PathVariable("walkerId") Long walkerId,
                                                            @PathVariable("reservationId") Long reservationId) {
        ReservationDto reservationDto = walkerService.finishReservation(walkerId, reservationId);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }
}