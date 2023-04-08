package com.tahafurkan.sandbox.Walk.With.Me.App.controller;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.DogDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dogs")
public class DogController {

    @Autowired
    DogService dogService;

    @GetMapping
    public ResponseEntity<List<DogDto>> getAll() {
        return ResponseEntity.ok(dogService.getAllDogs());
    }

    @GetMapping("{id}")
    public ResponseEntity<DogDto> getDog(@PathVariable("id") Long id) {

        DogDto dogDto = dogService.getDogById(id);
        return ResponseEntity.ok(dogDto);
    }

    @PostMapping
    public ResponseEntity<DogDto> create(@RequestBody DogDto dogDto) {
        DogDto savedDog = dogService.createDog(dogDto);
        return new ResponseEntity<>(savedDog, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<DogDto> update(@PathVariable("id") Long id,
                                         @RequestBody DogDto dogDto) {

        DogDto updatedDog = dogService.updateDog(id, dogDto);
        return ResponseEntity.ok(updatedDog);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        dogService.deleteDog(id);
        return ResponseEntity.ok("Dog deleted successfully");
    }
}