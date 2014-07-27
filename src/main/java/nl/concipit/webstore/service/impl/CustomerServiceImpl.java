package nl.concipit.webstore.service.impl;

import java.util.List;

import nl.concipit.webstore.domain.Customer;
import nl.concipit.webstore.domain.repository.CustomerRepository;
import nl.concipit.webstore.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repo;
	
	@Override
	public List<Customer> getAllCustomers() {
		return repo.getAllCustomers();
	}

}
