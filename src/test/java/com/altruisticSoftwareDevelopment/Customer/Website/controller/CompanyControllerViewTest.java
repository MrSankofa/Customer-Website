package com.altruisticSoftwareDevelopment.Customer.Website.controller;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;



@WebMvcTest(controllers = CompanyController.class)
@ContextConfiguration(classes = CompanyController.class)
public class CompanyControllerViewTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService; // Mocked service dependency

  @MockBean
  private CompanyService companyService;

  @Test
  void testShowAssignFinanceCompanyPage() throws Exception {
    // Arrange: Mock the service to return a sample customer
    Long customerId = 1L;
    Customer mockCustomer = new Customer();
    mockCustomer.setId(customerId);
    mockCustomer.setFullName("John Doe");
    mockCustomer.setCompany(new Company());

    Mockito.when(customerService.getCustomer(customerId)).thenReturn(mockCustomer);

    // Act & Assert: Perform the GET request and verify the response
    mockMvc.perform(MockMvcRequestBuilders.get("/financeCompany/assign/{id}", customerId))
        .andDo(MockMvcResultHandlers.print()) // Logs the details of the response to the console
        .andExpect(status().isOk()) // Assert the status is 200 OK
        .andExpect(view().name("new-finance")) // Assert the correct view name
        .andExpect(model().attributeExists("financeCompany")); // Assert "customer" is in the model
  }

  @Test
  void testShowFinanceListPage() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/financeList"))
        .andDo(MockMvcResultHandlers.print()); // Logs the details of the response to the console
  }
}
