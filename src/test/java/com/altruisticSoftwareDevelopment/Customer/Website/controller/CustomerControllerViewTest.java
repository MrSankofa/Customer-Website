package com.altruisticSoftwareDevelopment.Customer.Website.controller;


import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class) // Replace with your controller's class name
@ActiveProfiles("test")
public class CustomerControllerViewTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService; // Mocked service dependency

  @Test
  void testShowEditCustomerPage() throws Exception {
    // Arrange: Mock the service to return a sample customer
    Long customerId = 1L;
    Customer mockCustomer = new Customer();
    mockCustomer.setId(customerId);
    mockCustomer.setFullName("John Doe");
    mockCustomer.setFinanceCompany(new FinanceCompany());

    Mockito.when(customerService.getCustomer(customerId)).thenReturn(mockCustomer);

    // Act & Assert: Perform the GET request and verify the response
    mockMvc.perform(MockMvcRequestBuilders.get("/edit/{id}", customerId))
        .andExpect(status().isOk()) // Assert the status is 200 OK
        .andExpect(view().name("edit-customer")) // Assert the correct view name
        .andExpect(model().attributeExists("customer")) // Assert "customer" is in the model
        .andExpect(model().attribute("customer", mockCustomer)); // Assert the model attribute
  }

}
