package com.tahafurkan.sandbox.Walk.With.Me.App.service;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomer();

    CustomerDto findCustomerById(Long id);

    CustomerDto updateCustomer(Long customerId , CustomerDto customerDto);

    void deleteCustomer(Long id);




    List<DogDto> findCustomerDogs(Long customerId);

    DogDto findCustomerDog(Long customerId , Long dogId);

    CustomerDogDto addDogToCustomer(Long customerId , DogDto dogDto);

    CustomerDogDto removeDogToCustomer(Long customerId , Long dogId);

    CustomerDogDto updateDogToCustomer(Long customerId ,Long dogId, DogDto dogDto);



    CustomerReservationDetailsDto createReservations (Long customerId , Long dogId ,ReservationDto reservationDto);
    CustomerReservationDetailsDto findCustomerReservation(Long customerId , Long reservationId);

    Boolean checkReservation(Long customerId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<CustomerReservationDetailsDto> findAllCustomerReservations(Long customerId);

}