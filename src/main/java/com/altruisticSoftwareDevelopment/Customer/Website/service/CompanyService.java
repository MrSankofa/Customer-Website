package com.altruisticSoftwareDevelopment.Customer.Website.service;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
  List<Company> findAllFinanceCompanies();

  Company saveFinanceCompany(Company customer);

  Company getFinanceCompany(Long id);

  void deleteFinanceCompany(Long id);

  List<Company> saveAllFinanceCompany(List<Company> customers);

  void deleteAllCustomers();

  Optional<Company> updateFinanceCompanyById(Long id, Company updatedCompany);
}
