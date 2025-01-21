package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CustomerController {

  @Autowired
  CustomerService customerService;

  @GetMapping("/")
  public String viewHomePage(Model model) {

    final List<Customer> customers = customerService.findAllCustomers();

    model.addAttribute("customers", customers);

    return "index";
  }

  @GetMapping("/customers")
  @ResponseBody
  public ResponseEntity<List<Customer>> getCustomers() {

    return ResponseEntity.ok(customerService.findAllCustomers());
  }

  @PostMapping("/customer")
  @ResponseBody
  public ResponseEntity<Customer> createCustomer(@RequestBody final Customer customer) {

    return ResponseEntity.ok(customerService.saveCustomer(customer));
  }

  @PostMapping("/batch-save")
  @ResponseBody
  public ResponseEntity saveCustomer(@RequestBody final List<Customer> customers) {

    return ResponseEntity.ok( customerService.saveAllCustomer(customers));

  }
}
