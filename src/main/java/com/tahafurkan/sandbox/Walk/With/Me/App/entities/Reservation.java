package com.tahafurkan.sandbox.Walk.With.Me.App.entities;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walker_id")
    private Walker walker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "reservation_start_time")
    private LocalDateTime reservationStartTime;

    @Column(name = "reservation_end_time")
    private LocalDateTime reservationEndTime;

    private ReservationStatus reservationStatus;
}