package nl.concipit.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import nl.concipit.webstore.domain.Customer;
import nl.concipit.webstore.domain.repository.CustomerRepository;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

	private List<Customer> customers = new ArrayList<Customer>();
	
	public InMemoryCustomerRepository() {
		customers.add(new Customer("c123", "David Coppens", "Kraanven 23", 23));
	}
	
	@Override
	public List<Customer> getAllCustomers() {
		return customers;
	}

}
