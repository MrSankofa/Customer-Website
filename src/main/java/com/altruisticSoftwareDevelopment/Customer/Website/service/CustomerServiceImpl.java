package com.altruisticSoftwareDevelopment.Customer.Website.service;


import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.CustomerRepository;
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
  public List<Customer> saveAllCustomer(List<Customer> customers) {
    return customerRepository.saveAll(customers);
  }

  @Override
  public void deleteAllCustomers() {
    customerRepository.deleteAll();
  }

  @Override
  @Transactional
  public Customer assignCompany(Long customerId, FinanceCompany company) {

    Customer customer = customerRepository.findById(customerId).orElse(null);
    // get company
    if (customer == null) {
      // throw not such customer exception
    } else {
      // add the company to the customer.
      customer.setFinanceCompany(company);
      // add the customer to the company
      company.setCustomer(customer);
      customerRepository.save(customer);
    }

    // return the customer
    return customer;
  }
}
