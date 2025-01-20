package com.altruisticSoftwareDevelopment.Customer.Website.repository;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
