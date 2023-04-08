package com.tahafurkan.sandbox.Walk.With.Me.App.controller;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.*;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customerDto) {

        CustomerDto savedCustomer = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") Long id) {
        CustomerDto customerDto = customerService.findCustomerById(id);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("customerId") Long customerId,
                                                      @RequestBody CustomerDto customerDto) {
        CustomerDto updatedCustomer =
                customerService.updateCustomer(customerId, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @GetMapping("{customerId}/dogs")
    public ResponseEntity<List<DogDto>> findCustomerDogs(@PathVariable("customerId") Long customerId) {
        List<DogDto> DogDtos =
                customerService.findCustomerDogs(customerId);
        return ResponseEntity.ok(DogDtos);
    }

    @GetMapping("{customerId}/dogs/{dogId}")
    public ResponseEntity<DogDto> findCustomerDog(@PathVariable("customerId") Long customerId,
                                                          @PathVariable("dogId") Long dogId) {
        DogDto dogDto =
                customerService.findCustomerDog(customerId, dogId);
        return ResponseEntity.ok(dogDto);
    }


    @PostMapping("{customerId}/dogs")
    public ResponseEntity<CustomerDogDto> addDogToCustomer(@PathVariable("customerId") Long customerId,
                                                           @RequestBody DogDto dogDto) {

        CustomerDogDto savedCustomerDogDto =
                customerService.addDogToCustomer(customerId, dogDto);
        return new ResponseEntity<>(savedCustomerDogDto, HttpStatus.CREATED);
    }

    @DeleteMapping("{customerId}/dogs/{dogId}")
    public ResponseEntity<CustomerDogDto> removeDogFromCustomer(@PathVariable("customerId") Long customerId,
                                                                @PathVariable("dogId") Long dogId) {

        CustomerDogDto removedCustomerDogDto =
                customerService.removeDogToCustomer(customerId, dogId);
        if (removedCustomerDogDto != null) {
            return new ResponseEntity<>(removedCustomerDogDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("{customerId}/dogs/{dogId}")
    public ResponseEntity<CustomerDogDto> updateDogToCustomer(@PathVariable("customerId") Long customerId,
                                                              @PathVariable("dogId") Long dogId,
                                                              @RequestBody DogDto dogDto) {

        CustomerDogDto updatedDogToCustomer =
                customerService.updateDogToCustomer(customerId, dogId , dogDto);

        return ResponseEntity.ok(updatedDogToCustomer);
    }

    @GetMapping("{customerId}/reservations")
    public ResponseEntity<List<CustomerReservationDetailsDto>> getCustomerReservations(@PathVariable("customerId")
                                                                                       Long customerId) {

        List<CustomerReservationDetailsDto> customerReservationDetailsDtoList =
                customerService.findAllCustomerReservations(customerId);

        return ResponseEntity.ok(customerReservationDetailsDtoList);
    }

    @GetMapping("{customerId}/reservations/{reservationId}")
    public ResponseEntity<CustomerReservationDetailsDto> getReservation(@PathVariable("customerId") Long customerId,
                                                                        @PathVariable("reservationId") Long reservationId) {
        CustomerReservationDetailsDto customerReservationDetailsDto =
                customerService.findCustomerReservation(customerId, reservationId);
        return ResponseEntity.ok(customerReservationDetailsDto);
    }

    @PostMapping("/{customerId}/{dogId}/reservations")
    public ResponseEntity<CustomerReservationDetailsDto> createReservation(@PathVariable("customerId") Long customerId ,
                                                                           @PathVariable("dogId") Long dogId ,
                                                                           @RequestBody ReservationDto reservationDto) {

        CustomerReservationDetailsDto customerReservationDetailsDto =
                customerService.createReservations(customerId , dogId, reservationDto);

        return new ResponseEntity<>(customerReservationDetailsDto, HttpStatus.CREATED);
    }
}