package com.tahafurkan.sandbox.Walk.With.Me.App.controller;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.ReservationDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;


    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAll() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable("id") Long id) {
        ReservationDto reservationDto = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservationDto);
    }

    @PutMapping("{reservationId}")
    public ResponseEntity<ReservationDto> update(@PathVariable("reservationId") Long reservationId,
                                                 @RequestBody ReservationDto reservationDto) {
        ReservationDto updatedReservation =
                reservationService.updateReservation(reservationId, reservationDto);

        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation deleted successfully");
    }
}