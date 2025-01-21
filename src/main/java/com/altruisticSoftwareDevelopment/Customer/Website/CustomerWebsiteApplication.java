package com.altruisticSoftwareDevelopment.Customer.Website;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Customer;
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
		if(customerService.findAllCustomers().isEmpty()) {
			List<Customer> customers = Arrays.asList(
					Customer.builder()
							.fullName("Customer 1")
							.emailAddress("customer1@gmail.com")
							.address("Customer Address One")
							.age(30)
							.build(),
					Customer.builder()
							.fullName("Customer 2")
							.emailAddress("customer2@gmail.com")
							.address("Customer Address Two")
							.age(28)
							.build(),
					Customer.builder()
							.fullName("Customer 3")
							.emailAddress("customer3@gmail.com")
							.address("Customer Address Three")
							.age(32)
							.build()
			);
			System.out.println("Saving customers: " + customers);

			List<Customer> response = customerService.saveAllCustomer(customers);
			System.out.println("Customers saved successfully!" + response);
		}





		// Fetch and log all customers
		List<Customer> allCustomers = customerService.findAllCustomers();
		System.out.println("Retrieved customers from database: " + allCustomers);
	}
}
