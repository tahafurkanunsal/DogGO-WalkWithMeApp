package com.tahafurkan.sandbox.Walk.With.Me.App.repository;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Walker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkerRepository extends JpaRepository<Walker, Long> {
}
