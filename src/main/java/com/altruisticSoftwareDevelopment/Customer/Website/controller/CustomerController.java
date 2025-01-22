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
public class CustomerController {

  @Autowired
  CustomerService customerService;

  @GetMapping("/")
  // how to communicate java to HTML
  public String viewHomePage(Model model) {

    final List<Customer> customers = customerService.findAllCustomers();

    model.addAttribute("customers", customers);

    return "index";
  }

  @GetMapping("/new/customer")
  public String showNewCustomerPage(Model model) {
    Customer customer = new Customer();
    model.addAttribute("customer", customer);

    return "new-customer";
  }

  @GetMapping("/edit/{id}")
  public ModelAndView showEditCustomerPage(@PathVariable(name ="id") Long id) {
    ModelAndView modelAndView = new ModelAndView("edit-customer");
    Customer customer = customerService.getCustomer(id);

    modelAndView.addObject("customer", customer);

    return modelAndView;
  }

  @PostMapping("/update/{id}")
  public String updateCustomer(@PathVariable(name = "id") Long id, @ModelAttribute("customer") Customer customer, Model model) {

    if (!id.equals(customer.getId())) {
      model.addAttribute("message",
          "Cannot update, customer id " + customer.getId()
              + " doesn't match id to update: " + id + ".");
      return "error-page";
    }

    customerService.saveCustomer(customer);
    return "redirect:/";
  }

  @RequestMapping("/delete/{id}")
  public String deleteCustomer(@PathVariable(name = "id") Long id) {
    customerService.deleteCustomer(id);
    return "redirect:/";
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

  @PostMapping("/save")
  // how to communicate HTML to java
  public String saveCustomer(@ModelAttribute("customer")  Customer customer) {
    customerService.saveCustomer(customer);
    return "redirect:/";
  }

  @PostMapping("/batch-save")
  @ResponseBody
  public ResponseEntity saveCustomer(@RequestBody final List<Customer> customers) {

    return ResponseEntity.ok( customerService.saveAllCustomer(customers));

  }

}
