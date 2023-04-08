package com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerReservationDetailsDto {

    private Long customerId;
    private String customerName;
    List<ReservationDto> reservationDtos;

}