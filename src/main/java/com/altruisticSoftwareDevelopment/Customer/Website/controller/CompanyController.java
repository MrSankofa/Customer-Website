package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CompanyService;
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
public class CompanyController {

  @Autowired
  private CompanyService companyService;

  @Autowired
  private CustomerService customerService;

  @GetMapping("/page")
  public String viewCompanyHomePage(Model model) {
    List<Company> companies = companyService.findAllFinanceCompanies();

    model.addAttribute("companies", companies);
    return "/company/view-company";
  }

  @GetMapping("/new/page")
  public String showNewCompanyPage(Model model) {
    Company company = new Company();

    model.addAttribute("company", company);
    return "/company/new-company";
  }

  @GetMapping("{id}/edit/page")
  public ModelAndView showEditCompanyPage(@PathVariable(name ="id") Long id) {
    ModelAndView modelAndView = new ModelAndView("company/edit-company");
    Company company = companyService.getFinanceCompany(id);

    modelAndView.addObject("company", company);

    return modelAndView;
  }

  @GetMapping("/{id}/assign/page")
  public ModelAndView showAssignCompanyPage(@PathVariable(name ="id") Long customerId) {
      ModelAndView modelAndView = new ModelAndView("customer/assign-customer");
    // TODO: get all customers
    // TODO: add an attribute for all customers for the select element options
    return modelAndView;
  }



  @PostMapping("/save")
  public String saveFinanceCompany(
      @Valid @ModelAttribute("company") Company company,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      // If validation errors exist, redirect back with an error message
      redirectAttributes.addFlashAttribute("errorMessage", "Failed to save the finance company. Please check your input.");
      return "redirect:/company/page"; // Adjust to the appropriate page where the form is shown
    }

    try {
      companyService.saveFinanceCompany(company);
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
    Company company = companyService.getFinanceCompany(companyId);

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
      return ResponseEntity.ok(companyService.getFinanceCompany(id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @PostMapping("/{id}/update")
  public String updateCompany(@PathVariable(name = "id") Long id, @ModelAttribute("company") Company company, Model model) {

    if (!id.equals(company.getId())) {
      model.addAttribute("message",
          "Cannot update, company id " + company.getId()
              + " doesn't match id to update: " + id + ".");
      return "error/error";
    }

    companyService.saveFinanceCompany(company);
    return "redirect:/company/page";
  }

  @GetMapping
  @ResponseBody
  public ResponseEntity<?> findAllFinanceCompanies() {
    try {
      return ResponseEntity.ok(companyService.findAllFinanceCompanies());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/bulk")
  @ResponseBody
  public ResponseEntity<?> saveAllFinanceCompany(@Valid @RequestBody List<Company> financeCompanies) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(companyService.saveAllFinanceCompany(financeCompanies));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @RequestMapping("/{companyId}/delete")
  @ResponseBody
  public ResponseEntity<?> deleteFinanceCompanyById(@PathVariable Long companyId) {
    try {
      companyService.deleteFinanceCompany(companyId);
      return ResponseEntity.ok("The finance company with id: " + companyId + " was deleted");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


  @PutMapping("/{id}")
  @ResponseBody
  public ResponseEntity<?> updateFinanceCompany(@PathVariable Long id, @Valid @RequestBody Company company) {
    try {
      return ResponseEntity.ok(companyService.updateFinanceCompanyById(id, company));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
