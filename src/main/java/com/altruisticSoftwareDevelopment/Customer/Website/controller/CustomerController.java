package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CompanyService;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  CustomerService customerService;
  
  @Autowired
  CompanyService companyService;

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

  @GetMapping("{id}/edit/page")
  public ModelAndView showEditCustomerPage(@PathVariable(name ="id") Long id) {
    ModelAndView modelAndView = new ModelAndView("customer/edit-customer");
    Customer customer = customerService.getCustomer(id);

    modelAndView.addObject("customer", customer);

    return modelAndView;
  }

  @GetMapping("/{id}/assign/page")
  public ModelAndView showAssignCustomerPage(@PathVariable(name ="id") Long id) {

    // TODO: refactor to change the getCustomer return to Optional and add exceptions for no customer
    Customer customer = customerService.getCustomer(id);

    ModelAndView modelAndView = new ModelAndView("company/assign-company");
    // TODO: get all companies
    List<Company> companies = companyService.findAllFinanceCompanies().stream().filter( companyFilter -> companyFilter.getCustomer() == null).toList();
    // TODO: add an attribute for all companies for the select element options
    modelAndView.addObject("companies", companies);
    modelAndView.addObject("customer", customer);
    return modelAndView;
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

  @PostMapping("/{id}/update")
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

  @RequestMapping("/{id}/delete")
  public String deleteCustomer(@PathVariable(name = "id") Long id) {
    Customer customer = customerService.getCustomer(id);
    customer.removeCompany();
    customerService.deleteCustomer(id);
    return "redirect:/customer/page";
  }

  @RequestMapping("/{customerId}/assign/remove")
  public String removeFinanceCompanyById(@PathVariable Long customerId) {
    try {
      Customer customer = customerService.getCustomer(customerId);
      ;
      customerService.saveCustomer(customer.removeCompany());
      return "redirect:/customer/page";
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in removing finance company" + e.getMessage());
    }
  }

  @PostMapping("/assign")
  public String assignCustomer(@RequestParam Long customerId, @RequestParam Long companyId) {
    System.out.println("We got the customerId: " + customerId);
    System.out.println("We got the companyId: " + companyId);
    Company company = companyService.getFinanceCompany(companyId);

    if(company == null) {
      // TODO; throw no such company exception
    } else {
      // pass the customerId and the companyId to the service
      customerService.assignCompany(customerId, company);
    }

    return "redirect:/company/page";
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
