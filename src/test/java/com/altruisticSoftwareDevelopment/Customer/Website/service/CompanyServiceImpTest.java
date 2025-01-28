package com.altruisticSoftwareDevelopment.Customer.Website.service;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImpTest {
  @Mock
  private CompanyRepository companyRepository;

  @InjectMocks
  private CompanyServiceImp financeCompanyServiceImp;

  // tests that the service component does not corrupt or mismanage the data from the repo
  // but our service doesn't do anything but take information from the repo
  // so should really confirm that the methods in the repo are returning what we expect.
  @Test
  void findAllFinanceCompanies() {
    Customer customer = Customer.builder()
        .fullName("Brett")
        .emailAddress("Brett@gmail.com")
        .age(34)
        .address("123 anywhere dr, somewhere, Montana, 12942")
        .id(1L)
        .build();

    Company mockCompany = Company.builder()
        .companyName("Chase Bank")
        .customer(customer)
        .id(1L)
        .loanCapacity(100000.00)
        .build();

    List<Company> financeCompanies = new ArrayList<>(List.of(mockCompany));

    Mockito.when(companyRepository.findAll()).thenReturn(financeCompanies);

    List<Company> result = financeCompanyServiceImp.findAllFinanceCompanies();

    assertNotNull(result);
    assertEquals(financeCompanies.size(), result.size());
    assertEquals(financeCompanies.getFirst().getCustomer().getFullName(), customer.getFullName());


  }
}
