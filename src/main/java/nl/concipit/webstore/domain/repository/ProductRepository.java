package nl.concipit.webstore.domain.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.concipit.webstore.domain.Product;

public interface ProductRepository {
	List<Product> getAllProducts();
	
	Product getProductById(String productId);
	
	List<Product> getProductsByCategory(String category);
	
	List<Product> getProductsByManufacturer(String manufacturer);
	
	Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
	
	Set<Product> getProductsByPriceFilter(Map<String, List<String>> filterParams) throws ParseException;
	
	void addProduct(Product product);
}
