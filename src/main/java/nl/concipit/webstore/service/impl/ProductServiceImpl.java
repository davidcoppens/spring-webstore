package nl.concipit.webstore.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.concipit.webstore.domain.Product;
import nl.concipit.webstore.domain.repository.ProductRepository;
import nl.concipit.webstore.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository repository;
	
	@Override
	public List<Product> getAllProducts() {
		return repository.getAllProducts();
	}
	
	@Override
	public Product getProductById(String productId) {
		return repository.getProductById(productId);
	}
	
	@Override
	public List<Product> getProductsByCategory(String category) {
		return repository.getProductsByCategory(category);
	}

	@Override
	public Set<Product> getProductsByFilter(
			Map<String, List<String>> filterParams) {
		return repository.getProductsByFilter(filterParams);
	}

	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		return repository.getProductsByManufacturer(manufacturer);
	}

	@Override
	public Set<Product> getProductsByPriceFilter(
			Map<String, List<String>> filterParams) throws ParseException {
		return repository.getProductsByPriceFilter(filterParams);
	}
}
