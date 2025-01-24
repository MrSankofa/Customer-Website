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

  @GetMapping("/new")
  public String showNewFinancePage(Model model) {
    FinanceCompany financeCompany = new FinanceCompany();

    model.addAttribute("financeCompany", financeCompany);
    return "new-finance";
  }

  @GetMapping("/assign/create/{id}")
  public ModelAndView assignFinancePage(@PathVariable(name = "id") Long id) {
    ModelAndView modelAndView = new ModelAndView("assign-finance");
    FinanceCompany financeCompany = new FinanceCompany();

    Customer customer = customerService.getCustomer(id);
    financeCompany.setCustomer(customer);
    modelAndView.addObject("financeCompany", financeCompany);
    return modelAndView;
  }

  @GetMapping("/list")
  public String showFinanceListPage(Model model) {
    List<FinanceCompany> companies = financeCompanyService.findAllFinanceCompanies();

    model.addAttribute("companies", companies);
    return "view-finance";
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

  @PostMapping("/save")
  public String saveFinanceCompany(
      @Valid @ModelAttribute("financeCompany") FinanceCompany financeCompany,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      // If validation errors exist, redirect back with an error message
      redirectAttributes.addFlashAttribute("errorMessage", "Failed to save the finance company. Please check your input.");
      return "redirect:/"; // Adjust to the appropriate page where the form is shown
    }

    try {
      financeCompanyService.saveFinanceCompany(financeCompany);
      redirectAttributes.addFlashAttribute("successMessage", "Finance company saved successfully.");
      return "redirect:/"; // Redirect to the index page or wherever appropriate
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "An error occurred: " + e.getMessage());
      return "redirect:/"; // Redirect to the same page with the error message
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
