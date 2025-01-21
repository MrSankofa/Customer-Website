package com.altruisticSoftwareDevelopment.Customer.Website.service;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;

import java.util.List;
import java.util.Optional;

public interface FinanceCompanyService {
  List<FinanceCompany> findAllFinanceCompanies();

  FinanceCompany saveFinanceCompany(FinanceCompany customer);

  FinanceCompany getFinanceCompany(Long id);

  void deleteFinanceCompany(Long id);

  List<FinanceCompany> saveAllFinanceCompany(List<FinanceCompany> customers);

  void deleteAllCustomers();

  Optional<FinanceCompany> updateFinanceCompanyById(Long id, FinanceCompany updatedFinanceCompany);
}
