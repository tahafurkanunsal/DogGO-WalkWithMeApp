package com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto;

import lombok.Data;

import java.util.List;

@Data
public class WalkerReservationDetailsDto {

    private Long walkerId;
    private String walkerName;
    private List<ReservationDto> reservationDtos;
}