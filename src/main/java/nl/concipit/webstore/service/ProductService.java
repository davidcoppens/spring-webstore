package nl.concipit.webstore.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.concipit.webstore.domain.Product;

public interface ProductService {
	List<Product> getAllProducts();
	Product getProductById(String productId);
	List<Product> getProductsByCategory(String category);
	Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
	Set<Product> getProductsByPriceFilter(Map<String, List<String>> filterParams) throws ParseException;
	List<Product> getProductsByManufacturer(String manufacturer);
}
