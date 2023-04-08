package com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationDto {

    private Long reservationId;
    private Long dogId;
    private LocalDate reservationDate;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private ReservationStatus reservationStatus;

}