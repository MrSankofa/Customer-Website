package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
import com.altruisticSoftwareDevelopment.Customer.Website.service.FinanceCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/company")
public class FinanceCompanyController {

  @Autowired
  private FinanceCompanyService financeCompanyService;

  @Autowired
  private CustomerService customerService;

  @GetMapping("/new/page")
  public String showNewFinancePage(Model model) {
    FinanceCompany financeCompany = new FinanceCompany();

    model.addAttribute("financeCompany", financeCompany);
    return "/company/new-company";
  }

  @GetMapping("/assign/page/{id}")
  public ModelAndView showAssignCompanyPage(@PathVariable(name ="id") Long customerId) {
    // TODO: refactor to change the getCustomer return to Optional and add exceptions for no customer
    Customer customer = customerService.getCustomer(customerId);

    ModelAndView modelAndView = new ModelAndView("company/assign-company");
    // TODO: get all companies
    List<FinanceCompany> companies = financeCompanyService.findAllFinanceCompanies();
    // TODO: add an attribute for all companies for the select element options
    modelAndView.addObject("companies", companies);
    modelAndView.addObject("customer", customer);
    return modelAndView;
  }

  @GetMapping("/page")
  public String showFinanceListPage(Model model) {
    List<FinanceCompany> companies = financeCompanyService.findAllFinanceCompanies();

    model.addAttribute("companies", companies);
    return "/company/view-company";
  }

  @PostMapping("/save")
  public String saveFinanceCompany(
      @Valid @ModelAttribute("financeCompany") FinanceCompany financeCompany,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      // If validation errors exist, redirect back with an error message
      redirectAttributes.addFlashAttribute("errorMessage", "Failed to save the finance company. Please check your input.");
      return "redirect:/company/page"; // Adjust to the appropriate page where the form is shown
    }

    try {
      financeCompanyService.saveFinanceCompany(financeCompany);
      redirectAttributes.addFlashAttribute("successMessage", "Finance company saved successfully.");
      return "redirect:/company/page"; // Redirect to the index page or wherever appropriate
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "An error occurred: " + e.getMessage());
      return "redirect:/company/page"; // Redirect to the same page with the error message
    }
  }

  @PostMapping("/assign")
  public String assignCompany(@RequestParam Long customerId, @RequestParam Long companyId) {
    System.out.println("We got the customerId: " + customerId);
    System.out.println("We got the companyId: " + companyId);
    FinanceCompany company = financeCompanyService.getFinanceCompany(companyId);

    if(company == null) {
      // TODO; throw no such company exception
    } else {
      // pass the customerId and the companyId to the service
      customerService.assignCompany(customerId, company);
    }

    return "redirect:/customer/page";
  }

  @GetMapping("/{id}")
  @ResponseBody
  public ResponseEntity<?> findFinanceCompanyById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(financeCompanyService.getFinanceCompany(id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @GetMapping
  @ResponseBody
  public ResponseEntity<?> findAllFinanceCompanies() {
    try {
      return ResponseEntity.ok(financeCompanyService.findAllFinanceCompanies());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/bulk")
  @ResponseBody
  public ResponseEntity<?> saveAllFinanceCompany(@Valid @RequestBody List<FinanceCompany> financeCompanies) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(financeCompanyService.saveAllFinanceCompany(financeCompanies));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public ResponseEntity<?> deleteFinanceCompanyById(@PathVariable Long id) {
    try {
      financeCompanyService.deleteFinanceCompany(id);
      return ResponseEntity.ok("The finance company with id: " + id + " was deleted");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  @ResponseBody
  public ResponseEntity<?> updateFinanceCompany(@PathVariable Long id, @Valid @RequestBody FinanceCompany financeCompany) {
    try {
      return ResponseEntity.ok(financeCompanyService.updateFinanceCompanyById(id, financeCompany));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
