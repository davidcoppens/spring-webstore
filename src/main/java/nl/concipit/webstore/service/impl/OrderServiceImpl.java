package nl.concipit.webstore.service.impl;

import nl.concipit.webstore.domain.Product;
import nl.concipit.webstore.domain.repository.ProductRepository;
import nl.concipit.webstore.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductRepository repository;
	
	@Override
	public void processOrder(String productId, int quantity) {
		Product product = repository.getProductById(productId);
		
		if (product.getUnitsInStock() < quantity) {
			throw new IllegalArgumentException("Not enough in stock. Available: " + product.getUnitsInStock());
			
		} 
		product.setUnitsInStock(product.getUnitsInStock() - quantity);
	}

}
