package com.tahafurkan.sandbox.Walk.With.Me.App.service.impl;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Customer;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Dog;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Reservation;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.*;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.enums.ReservationStatus;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.ReservationConflictException;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.ResourceNotFoundException;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.CustomerRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.DogRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.ReservationRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    DogRepository dogRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {

        Customer customer = modelMapper.map(customerDto, Customer.class);

        Customer savedCustomer = customerRepository.save(customer);

        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> getAllCustomer() {

        List<Customer> customerList = customerRepository.findAll();

        return customerList.stream().map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public List<DogDto> findCustomerDogs(Long customerId) {
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        if (customer != null) {
            return customer.getDogs().stream().
                    map(dog -> modelMapper.map(dog, DogDto.class)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public DogDto findCustomerDog(Long customerId, Long dogId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        Dog dog = customer.getDogs().stream()
                .filter(d -> d.getId().equals(dogId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Dog", "id", dogId));

        return modelMapper.map(dog, DogDto.class);
    }

    @Override
    public CustomerDogDto addDogToCustomer(Long customerId, DogDto dogDto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        Dog newDog = new Dog();
        newDog.setName(dogDto.getName());
        newDog.setAge(dogDto.getAge());
        newDog.setBreed(dogDto.getBreed());
        newDog.setOwner(customer);

        customer.getDogs().add(newDog);

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDogDto customerDogDto = modelMapper.map(savedCustomer, CustomerDogDto.class);

        List<DogDto> dogDtos = savedCustomer.getDogs().stream()
                .map(dog -> modelMapper.map(dog, DogDto.class))
                .collect(Collectors.toList());

        customerDogDto.setDogs(dogDtos);
        return customerDogDto;
    }

    @Override
    public CustomerDogDto removeDogToCustomer(Long customerId, Long dogId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        Optional<Dog> optionalDog = customer.getDogs().stream()
                .filter(dog -> dog.getId().equals(dogId))
                .findFirst();

        if (optionalDog.isPresent()) {
            Dog dogToRemove = optionalDog.get();
            customer.getDogs().remove(dogToRemove);
            dogToRemove.setOwner(null);
            customerRepository.save(customer);
            return modelMapper.map(customer, CustomerDogDto.class);
        } else {
            return null;
        }
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {

        Customer existingCustomer = customerRepository.findById(customerId).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        existingCustomer.setName(customerDto.getName());
        existingCustomer.setLastName(customerDto.getLastName());
        existingCustomer.setEmail(customerDto.getEmail());
        existingCustomer.setPassword(customerDto.getPassword());

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        return modelMapper.map(updatedCustomer, CustomerDto.class);

    }

    @Override
    public void deleteCustomer(Long id) {

        Customer existingCustomer = customerRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDogDto updateDogToCustomer(Long customerId, Long dogId, DogDto dogDto) {


        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        List<Dog> dogs = customer.getDogs();

        Optional<Dog> optionalDog = dogs.stream().filter(d -> d.getId().equals(dogId)).findFirst();

        if (optionalDog.isPresent()) {
            Dog dog = optionalDog.get();
            dog.setName(dogDto.getName());
            dog.setAge(dogDto.getAge());
            dog.setBreed(dogDto.getBreed());
            Dog savedDog = dogRepository.save(dog);

            CustomerDogDto customerDogDto = modelMapper.map(customer, CustomerDogDto.class);

            List<DogDto> dogDtos = customerDogDto.getDogs().stream()
                    .filter(d -> d.getId().equals(savedDog.getId()))
                    .map(d -> modelMapper.map(savedDog, DogDto.class))
                    .collect(Collectors.toList());

            customerDogDto.setDogs(dogDtos);

            return customerDogDto;
        } else {
            throw new ResourceNotFoundException("Dog", "id", dogId);
        }
    }

    @Override
    public CustomerReservationDetailsDto createReservations(Long customerId, Long dogId, ReservationDto reservationDto) {


        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        Dog dog = dogRepository.findById(dogId)
                .orElseThrow(() -> new ResourceNotFoundException("Dog", "id", dogId));

        LocalDateTime start = reservationDto.getReservationStartTime();
        LocalDateTime end = reservationDto.getReservationEndTime();

        if (checkReservation(customerId, start, end)) {
            throw new ReservationConflictException("There is a conflicting reservation for the given time range");
        }

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setDog(dog);
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setReservationStartTime(start);
        reservation.setReservationEndTime(end);
        reservation.setReservationStatus(ReservationStatus.PENDING);

        reservationRepository.save(reservation);

        List<Reservation> reservations = reservationRepository.findByCustomerAndDog(customer, dog);

        List<ReservationDto> reservationDtos = reservations.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        CustomerReservationDetailsDto customerReservationDetailsDto = new CustomerReservationDetailsDto();
        customerReservationDetailsDto.setCustomerId(customerId);
        customerReservationDetailsDto.setCustomerName(customer.getName());
        customerReservationDetailsDto.setReservationDtos(reservationDtos);

        return customerReservationDetailsDto;
    }

    @Override
    public List<CustomerReservationDetailsDto> findAllCustomerReservations(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        List<CustomerReservationDetailsDto> reservationDtos = new ArrayList<>();

        for (Reservation reservation : customer.getReservations()) {
            CustomerReservationDetailsDto dto = new CustomerReservationDetailsDto();
            dto.setCustomerId(customerId);
            dto.setCustomerName(customer.getName());
            dto.setReservationDtos(Collections.singletonList(modelMapper.map(reservation, ReservationDto.class)));
            reservationDtos.add(dto);
        }

        return reservationDtos;
    }

    @Override
    public CustomerReservationDetailsDto findCustomerReservation(Long customerId, Long reservationId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        List<Reservation> reservations = customer.getReservations();
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservationId));

        CustomerReservationDetailsDto detailsDto = new CustomerReservationDetailsDto();
        detailsDto.setCustomerId(customerId);
        detailsDto.setCustomerName(customer.getName());

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReservationId(reservation.getId());
        reservationDto.setDogId(reservation.getDog().getId());
        reservationDto.setReservationDate(reservation.getReservationDate());
        reservationDto.setReservationStartTime(reservation.getReservationStartTime());
        reservationDto.setReservationEndTime(reservation.getReservationEndTime());
        reservationDto.setReservationStatus(reservation.getReservationStatus());

        detailsDto.setReservationDtos(Collections.singletonList(reservationDto));

        return detailsDto;

    }

    @Override
    public Boolean checkReservation(Long customerId, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        for (Reservation reservation : customer.getReservations()) {
            if (reservation.getReservationStartTime().isBefore(endDateTime)
                    && reservation.getReservationEndTime().isAfter(startDateTime)) {
                return true;
            }
        }
        return false;
    }

    private CustomerDto mapToDTO(Customer customer) {

        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;

    }


    //Map to Entity function
    private Customer mapToEntity(CustomerDto customerDto) {

        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customer;
    }

    //Map to Dto function
    private ReservationDto mapToDTO(Reservation reservation) {

        ReservationDto reservationDto = modelMapper.map(reservation, ReservationDto.class);
        return reservationDto;

    }
    //Map to entity function
    private Reservation mapToEntity(ReservationDto reservationDto) {

        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        return reservation;

    }
}