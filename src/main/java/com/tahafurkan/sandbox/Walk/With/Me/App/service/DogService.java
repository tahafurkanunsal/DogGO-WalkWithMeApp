package com.tahafurkan.sandbox.Walk.With.Me.App.service;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.DogDto;

import java.util.List;

public interface DogService {

    List<DogDto> getAllDogs();

    DogDto getDogById(Long id);

    DogDto createDog(DogDto dogDto);

    DogDto updateDog(Long id, DogDto dogDto);

    void deleteDog(Long id);
}