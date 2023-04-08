package com.tahafurkan.sandbox.Walk.With.Me.App.service.impl;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Dog;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.DogDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.ResourceNotFoundException;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.DogRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.DogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogServiceImpl implements DogService {

    @Autowired
    DogRepository dogRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<DogDto> getAllDogs() {

        List<Dog> dogList = dogRepository.findAll();

        return dogList.stream().map(dog -> modelMapper.map(dog, DogDto.class))
                .collect(Collectors.toList());
    }

    public DogDto getDogById(Long id) {

        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dog", "id", id));

        return modelMapper.map(dog, DogDto.class);
    }

    public DogDto createDog(DogDto dogDto) {

        Dog dog = modelMapper.map(dogDto, Dog.class);

        Dog savedDog = dogRepository.save(dog);

        return modelMapper.map(savedDog, DogDto.class);
    }

    public DogDto updateDog(Long id, DogDto dogDto) {

        Dog existingDog = dogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dog", "id", id));


        existingDog.setName(dogDto.getName());
        existingDog.setAge(dogDto.getAge());
        existingDog.setBreed(dogDto.getBreed());

        Dog updatedDog = dogRepository.save(existingDog);

        return modelMapper.map(updatedDog, DogDto.class);
    }

    public void deleteDog(Long id) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dog", "id", id));

        dogRepository.deleteById(id);

    }
}