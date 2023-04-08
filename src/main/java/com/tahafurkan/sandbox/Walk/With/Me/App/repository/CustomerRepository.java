package com.tahafurkan.sandbox.Walk.With.Me.App.repository;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
