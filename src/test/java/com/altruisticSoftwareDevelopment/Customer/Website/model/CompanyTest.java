package com.altruisticSoftwareDevelopment.Customer.Website.model;

import com.altruisticSoftwareDevelopment.Customer.Website.CustomerWebsiteApplication;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.CustomerRepository;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = CustomerWebsiteApplication.class)
@ActiveProfiles("test")
class CompanyTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CompanyRepository companyRepository;

  @Test
  public void testCustomerOwnsRelationship() {
    // Step 1: Create a FinanceCompany
    Company company = Company.builder()
        .companyName("Finance Co.")
        .loanCapacity(500000.0)
        .build();

    // Step 2: Create a Customer and set the relationship
    Customer customer = Customer.builder()
        .fullName("John Doe")
        .emailAddress("john.doe@example.com")
        .age(30)
        .address("123 Main Street")
        .company(company)
        .build();

    // Set the inverse side (for bidirectional mapping)
    company.setCustomer(customer);

    // Step 3: Persist the Customer (and cascade the FinanceCompany)
    Customer savedCustomer = customerRepository.save(customer);

    // Step 4: Fetch Customer and verify
    Customer fetchedCustomer = customerRepository.findById(savedCustomer.getId()).orElseThrow();
    assertThat(fetchedCustomer.getCompany()).isNotNull();
    assertThat(fetchedCustomer.getCompany().getCompanyName()).isEqualTo("Finance Co.");

    // Step 5: Verify the inverse relationship
    Company fetchedCompany = companyRepository.findById(company.getId()).orElseThrow();
    assertThat(fetchedCompany.getCustomer()).isNotNull();
    assertThat(fetchedCompany.getCustomer().getFullName()).isEqualTo("John Doe");

    // Step 6: Verify the foreign key column (optional)
    assertThat(fetchedCustomer.getCompany().getId()).isEqualTo(company.getId());
  }

}
