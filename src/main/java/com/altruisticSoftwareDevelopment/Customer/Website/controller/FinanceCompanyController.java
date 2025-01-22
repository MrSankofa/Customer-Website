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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/financeCompany")
public class FinanceCompanyController {

  @Autowired
  private FinanceCompanyService financeCompanyService;

  @Autowired
  private CustomerService customerService;

  @GetMapping("/assign/{id}")
  public ModelAndView showNewFinancePage(@PathVariable(name = "id") Long id) {
    ModelAndView modelAndView = new ModelAndView("new-finance");
    FinanceCompany financeCompany = new FinanceCompany();

    Customer customer = customerService.getCustomer(id);
    List<FinanceCompany> financeCompanies = financeCompanyService.findAllFinanceCompanies();
    financeCompany.setCustomer(customer);
    modelAndView.addObject("financeCompanies", financeCompanies);
    modelAndView.addObject("targetCustomer", customer);
    modelAndView.addObject("financeCompany", financeCompany);
    return modelAndView;
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

  @PostMapping
  @ResponseBody
  public ResponseEntity<?> saveFinanceCompany(@Valid @RequestBody FinanceCompany financeCompany) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(financeCompanyService.saveFinanceCompany(financeCompany));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
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
