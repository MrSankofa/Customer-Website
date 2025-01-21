package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.FinanceCompanyRepository;
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
class FinanceCompanyControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private FinanceCompanyRepository financeCompanyRepository;

  @BeforeEach
  void setup() {
    financeCompanyRepository.deleteAll();
    financeCompanyRepository.save(FinanceCompany.builder()
        .companyName("Test Company")
        .loanCapacity(500000.0)
        .build());
  }

  @Test
  void findAllFinanceCompanies() {
    ResponseEntity<List<FinanceCompany>> response = restTemplate.exchange(
        "/financeCompany",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<FinanceCompany>>() {}
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isEmpty());
    assertEquals("Test Company", response.getBody().get(0).getCompanyName());
  }
}
