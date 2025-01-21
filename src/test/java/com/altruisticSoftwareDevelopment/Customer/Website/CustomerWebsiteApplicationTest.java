//package com.altruisticSoftwareDevelopment.Customer.Website;
//
//import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
//import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;
//import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Arrays;
//import java.util.List;
//
//@SpringBootTest
//@ActiveProfiles("Test")
//class CustomerWebsiteApplicationTest {
//
//  @Autowired
//  private CustomerService customerService;
//
//  @BeforeEach
//  void setUp() {
//    customerService.deleteAllCustomers();
//  }
//
//  // TODO: revise test so that it works separate from what's in the data base. Or confirm that the two customers are in there
//
//  @Test
//  public void testSaveAndRetrieveCustomers() {
//    // Save customers
//    List<Customer> customers = Arrays.asList(
//        new Customer(null, "Customer 1", "customer1@gmail.com", 30, "Customer Address One", FinanceCompany.builder().build()),
//        new Customer(null, "Customer 2", "customer2@gmail.com", 28, "Customer Address Two", FinanceCompany.builder().build())
//    );
//
//    customerService.saveAllCustomer(customers);
//
//    // Retrieve customers
//    List<Customer> retrievedCustomers = customerService.findAllCustomers();
//    System.out.println("Retrieved customers: " + retrievedCustomers);
//
//    Assertions.assertEquals(5, retrievedCustomers.size());
//  }
//
//}