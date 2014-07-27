package nl.concipit.webstore.domain.repository;

import java.util.List;

import nl.concipit.webstore.domain.Customer;

public interface CustomerRepository {
	List<Customer> getAllCustomers();
}
