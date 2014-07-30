package nl.concipit.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.concipit.webstore.domain.Product;
import nl.concipit.webstore.domain.repository.ProductRepository;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductRepository implements ProductRepository {

	private List<Product> listOfProducts = new ArrayList<Product>();

	public InMemoryProductRepository() {
		Product iphone = new Product("P1234", "iPhone 5s", new BigDecimal(500));
		iphone.setDescription("Apple iPhone 5s smartphone with 4.00-inch 640x1136 display and 8-megapixel rear camera");
		iphone.setCategory("Smart Phone");
		iphone.setManufacturer("Apple");
		iphone.setUnitsInStock(1000);
		Product laptop_dell = new Product("P1235", "Dell Inspiron",
				new BigDecimal(700));
		laptop_dell
				.setDescription("Dell Inspiron 14-inch Laptop (Black) with 3rd Generation Intel Core processors");
		laptop_dell.setCategory("Laptop");

		laptop_dell.setManufacturer("Dell");
		laptop_dell.setUnitsInStock(1000);
		Product tablet_Nexus = new Product("P1236", "Nexus 7", new BigDecimal(
				300));
		tablet_Nexus
				.setDescription("Google Nexus 7 is the lightest	7 inch tablet With a quad-core Qualcomm Snapdragon™ S4 Pro processor");
		tablet_Nexus.setCategory("Tablet");
		tablet_Nexus.setManufacturer("Google");
		tablet_Nexus.setUnitsInStock(1000);
		listOfProducts.add(iphone);
		listOfProducts.add(laptop_dell);
		listOfProducts.add(tablet_Nexus);
	}

	@Override
	public List<Product> getAllProducts() {
		return listOfProducts;
	}

	@Override
	public Product getProductById(String productId) {
		Product productById = null;
		for (Product product : listOfProducts) {
			if (product != null && product.getProductId() != null
					&& product.getProductId().equals(productId)) {
				productById = product;
				break;
			}
		}
		if (productById == null) {
			throw new IllegalArgumentException(
					"No products found with the product id: " + productId);
		}
		return productById;
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		List<Product> productsByCategory = new ArrayList<Product>();
		for (Product p : listOfProducts) {
			if (category.equalsIgnoreCase(p.getCategory())) {
				productsByCategory.add(p);
			}
		}
		return productsByCategory;
	}

	@Override
	public Set<Product> getProductsByFilter(
			Map<String, List<String>> filterParams) {
		Set<Product> result = new HashSet<Product>();

		Set<String> criteria = filterParams.keySet();

		for (Product p : listOfProducts) {
			if (criteria.contains("brand")) {
				for (String brand : filterParams.get("brand")) {

					if (brand.equalsIgnoreCase(p.getManufacturer())) {

						result.add(p);
					}
				}
			}
			if (criteria.contains("category")) {
				for (String category : filterParams.get("category")) {
					if (category.equalsIgnoreCase(p.getCategory())) {
						result.add(p);
					}
				}
			}
		}

		return result;
	}
	
	@Override
	public Set<Product> getProductsByPriceFilter(
			Map<String, List<String>> filterParams) throws ParseException {
		
		Set<Product> result = new HashSet<Product>();

		Set<String> criteria = filterParams.keySet();
		BigDecimal low = null;
		BigDecimal high = null;
		DecimalFormat f = new DecimalFormat();
		f.setParseBigDecimal(true);
		if (criteria.contains("low")) {
			low = (BigDecimal) f.parse(filterParams.get("low").get(0));			
		}
		if (criteria.contains("high")) {
			high = (BigDecimal) f.parse(filterParams.get("high").get(0));			
		}

		for (Product p : listOfProducts) {
			boolean add = true;
			if (low != null && p.getUnitPrice().compareTo(low) >= 0) {
				add = true;
			} else {
				add = false;
			}
			if (high != null && p.getUnitPrice().compareTo(high) < 0) {
				add = true;
			} else {
				add = false;
			}
			if (add) {
				result.add(p);
			}
		}

		return result;
	}

	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		List<Product> result = new ArrayList<Product>();
		for (Product p : listOfProducts) {
			if (p.getManufacturer().equalsIgnoreCase(manufacturer)) {
				result.add(p);
			}
		}
		return result;
	}
	
	@Override
	public void addProduct(Product product) {
		this.listOfProducts.add(product);
	}
}
