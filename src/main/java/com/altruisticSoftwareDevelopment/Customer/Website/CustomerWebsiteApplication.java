package com.altruisticSoftwareDevelopment.Customer.Website;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;
import com.altruisticSoftwareDevelopment.Customer.Website.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CustomerWebsiteApplication implements CommandLineRunner {

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) { SpringApplication.run(CustomerWebsiteApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running CommandLineRunner...");
		if (customerService.findAllCustomers().isEmpty()) {
			// Create Customers and FinanceCompanies together
			Customer customer1 = Customer.builder()
					.fullName("Customer 1")
					.emailAddress("customer1@gmail.com")
					.address("Customer Address One")
					.age(30)
					.build();

			FinanceCompany financeCompany1 = FinanceCompany.builder()
					.companyName("Finance Company 1")
					.loanCapacity(100000.0)
					.build();
			// Set bidirectional relationship
			customer1.setFinanceCompany(financeCompany1);
			financeCompany1.setCustomer(customer1);

			Customer customer2 = Customer.builder()
					.fullName("Customer 2")
					.emailAddress("customer2@gmail.com")
					.address("Customer Address Two")
					.age(28)
					.build();

			FinanceCompany financeCompany2 = FinanceCompany.builder()
					.companyName("Finance Company 2")
					.loanCapacity(200000.0)
					.build();
			// Set bidirectional relationship
			customer2.setFinanceCompany(financeCompany2);
			financeCompany2.setCustomer(customer2);

			Customer customer3 = Customer.builder()
					.fullName("Customer 3")
					.emailAddress("customer3@gmail.com")
					.address("Customer Address Three")
					.age(32)
					.build();

			FinanceCompany financeCompany3 = FinanceCompany.builder()
					.companyName("Finance Company 3")
					.loanCapacity(300000.0)
					.build();
			// Set bidirectional relationship
			customer3.setFinanceCompany(financeCompany3);
			financeCompany3.setCustomer(customer3);

			// Save Customers and FinanceCompanies together
			List<Customer> customers = Arrays.asList(customer1, customer2, customer3);
			List<Customer> response = customerService.saveAllCustomer(customers);

			System.out.println("Customers saved successfully! " + response);
		}




		// Fetch and log all customers
		List<Customer> allCustomers = customerService.findAllCustomers();
		System.out.println("Retrieved customers from database: " + allCustomers);
	}
}
