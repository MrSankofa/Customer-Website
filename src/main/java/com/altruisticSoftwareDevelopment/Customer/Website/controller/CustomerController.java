package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  CustomerService customerService;

  @GetMapping("/page")
  // how to communicate java to HTML
  public String viewHomePage(Model model) {

    final List<Customer> customers = customerService.findAllCustomers();

    model.addAttribute("customers", customers);

    return "index";
  }

  @GetMapping("/new/page")
  public String showNewCustomerPage(Model model) {
    Customer customer = new Customer();
    model.addAttribute("customer", customer);

    return "customer/new-customer";
  }

  @GetMapping("/edit/{id}")
  public ModelAndView showEditCustomerPage(@PathVariable(name ="id") Long id) {
    ModelAndView modelAndView = new ModelAndView("customer/edit-customer");
    Customer customer = customerService.getCustomer(id);

    modelAndView.addObject("customer", customer);

    return modelAndView;
  }

  @GetMapping("/assign/create/{id}")
  public ModelAndView showCreateCustomerPage(@PathVariable(name ="id") Long id) {
    ModelAndView modelAndView = new ModelAndView("assign-customer");
    // TODO: get all customers
    // TODO: add an attribute for all customers for the select element options
    return modelAndView;
  }

  @PostMapping("/update/{id}")
  public String updateCustomer(@PathVariable(name = "id") Long id, @ModelAttribute("customer") Customer customer, Model model) {

    if (!id.equals(customer.getId())) {
      model.addAttribute("message",
          "Cannot update, customer id " + customer.getId()
              + " doesn't match id to update: " + id + ".");
      return "error/error";
    }

    customerService.saveCustomer(customer);
    return "redirect:/customer/page";
  }

  @RequestMapping("/delete/{id}")
  public String deleteCustomer(@PathVariable(name = "id") Long id) {
    customerService.deleteCustomer(id);
    return "redirect:/company/page";
  }

  @GetMapping
  @ResponseBody
  public ResponseEntity<List<Customer>> getCustomers() {
    return ResponseEntity.ok(customerService.findAllCustomers());
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<Customer> createCustomer(@RequestBody final Customer customer) {
    return ResponseEntity.ok(customerService.saveCustomer(customer));
  }

  @PostMapping("/save")
  // how to communicate HTML to java
  public String saveCustomer(@ModelAttribute("customer")  Customer customer) {
    customerService.saveCustomer(customer);
    return "redirect:/customer/page";
  }

  @PostMapping("/batch-save")
  @ResponseBody
  public ResponseEntity saveCustomer(@RequestBody final List<Customer> customers) {
    return ResponseEntity.ok( customerService.saveAllCustomer(customers));
  }

}
