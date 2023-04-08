package com.tahafurkan.sandbox.Walk.With.Me.App.repository;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
