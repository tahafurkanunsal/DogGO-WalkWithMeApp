package com.tahafurkan.sandbox.Walk.With.Me.App.repository;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Customer;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Dog;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerAndDog(Customer customer, Dog dog);
}
