package com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDogDto {

    private Long id;
    private String name;
    private String lastName;
    private List<DogDto> dogs;
}