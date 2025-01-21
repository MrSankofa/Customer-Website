package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;
import com.altruisticSoftwareDevelopment.Customer.Website.service.FinanceCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/financeCompany")
public class FinanceCompanyController {

  @Autowired
  private FinanceCompanyService financeCompanyService;

  @GetMapping("/{id}")
  public ResponseEntity<?> findFinanceCompanyById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(financeCompanyService.getFinanceCompany(id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<?> findAllFinanceCompanies() {
    try {
      return ResponseEntity.ok(financeCompanyService.findAllFinanceCompanies());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> saveFinanceCompany(@Valid @RequestBody FinanceCompany financeCompany) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(financeCompanyService.saveFinanceCompany(financeCompany));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/bulk")
  public ResponseEntity<?> saveAllFinanceCompany(@Valid @RequestBody List<FinanceCompany> financeCompanies) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(financeCompanyService.saveAllFinanceCompany(financeCompanies));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteFinanceCompanyById(@PathVariable Long id) {
    try {
      financeCompanyService.deleteFinanceCompany(id);
      return ResponseEntity.ok("The finance company with id: " + id + " was deleted");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateFinanceCompany(@PathVariable Long id, @Valid @RequestBody FinanceCompany financeCompany) {
    try {
      return ResponseEntity.ok(financeCompanyService.updateFinanceCompanyById(id, financeCompany));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
