package com.altruisticSoftwareDevelopment.Customer.Website.service;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;

import java.util.List;


public interface CustomerService {

  List<Customer> findAllCustomers();

  Customer saveCustomer(Customer customer);

  Customer getCustomer(Long id);

  void deleteCustomer(Long id);

  List<Customer> saveAllCustomer(List<Customer> customers);

  void deleteAllCustomers();

  Customer assignCompany(Long customerId, Company company);

  void flush();

  // First transaction: remove the company association
    void removeCompanyFromCustomer(Long id);

  // Second transaction: delete the customer
    void deleteCustomerById(Long id);

    void deleteCustomerWithCompanyCleanup(Long id);
}
