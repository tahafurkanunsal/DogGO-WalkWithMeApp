package com.tahafurkan.sandbox.Walk.With.Me.App.repository;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}