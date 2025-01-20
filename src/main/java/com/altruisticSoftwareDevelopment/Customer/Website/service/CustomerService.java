package com.altruisticSoftwareDevelopment.Customer.Website.service;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;

import java.util.List;


public interface CustomerService {

  List<Customer> getAllCustomers();

  Customer saveCustomer(Customer customer);

  Customer getCustomer(Long id);

  void deleteCustomer(Long id);

  List<Customer> saveAllCustomer(List<Customer> customers);
}
