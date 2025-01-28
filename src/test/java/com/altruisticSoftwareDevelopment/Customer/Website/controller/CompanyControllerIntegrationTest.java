package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private CompanyRepository companyRepository;

  @BeforeEach
  void setup() {
    companyRepository.deleteAll();
    companyRepository.save(Company.builder()
        .companyName("Test Company")
        .loanCapacity(500000.0)
        .build());
  }

  @Test
  void findAllFinanceCompanies() {
    ResponseEntity<List<Company>> response = restTemplate.exchange(
        "/financeCompany",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Company>>() {}
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isEmpty());
    assertEquals("Test Company", response.getBody().get(0).getCompanyName());
  }
}
