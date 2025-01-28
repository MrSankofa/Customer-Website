package com.altruisticSoftwareDevelopment.Customer.Website.service;


import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService{

  // constructor injection thanks to @RequiredArgsConstructor
  //  private final CustomerRepository customerRepository;

  // Constructor-injected dependency
  private final CustomerRepository customerRepository;

  // Manual constructor to handle dependency injection
  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }


  @Override
  @Transactional
  public List<Customer> findAllCustomers() {
    return customerRepository.findAll();
  }

  @Override
  @Transactional
  public Customer saveCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Customer getCustomer(Long id) {
    return customerRepository.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public void deleteCustomer(Long id) {
    customerRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void removeCompanyFromCustomer(Long id) {
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    customer.setCompany(null); // Break the association
    customerRepository.save(customer); // Persist changes
    customerRepository.flush(); // Ensure the database is updated immediately
  }

  @Override
  @Transactional
  public void deleteCustomerById(Long id) {
    customerRepository.deleteById(id);
  }

  @Transactional
  public void deleteCustomerWithCompanyCleanup(Long id) {
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    customer.setCompany(null); // Remove association with the Company
    customerRepository.save(customer); // Save the changes
    customerRepository.deleteById(id); // Delete the customer
  }

  @Override
  @Transactional
  public List<Customer> saveAllCustomer(List<Customer> customers) {
    return customerRepository.saveAll(customers);
  }

  @Override
  public void deleteAllCustomers() {
    customerRepository.deleteAll();
  }

  @Override
  @Transactional
  public Customer assignCompany(Long customerId, Company company) {

    Customer customer = customerRepository.findById(customerId).orElse(null);
    // get company
    if (customer == null) {
      // throw not such customer exception
    } else {
      // add the company to the customer.
      customer.setCompany(company);
      // add the customer to the company
      company.setCustomer(customer);
      customerRepository.save(customer);
    }

    // return the customer
    return customer;
  }

  @Override
  public void flush() {
    customerRepository.flush();;
  }
}
