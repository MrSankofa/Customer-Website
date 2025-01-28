package com.altruisticSoftwareDevelopment.Customer.Website.repository;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
