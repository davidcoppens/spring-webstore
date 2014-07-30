package nl.concipit.webstore.controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.concipit.webstore.domain.Product;
import nl.concipit.webstore.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@RequestMapping
	public String list(Model model) {

		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@RequestMapping("/all")
	public String allProducts(Model model) {
		return list(model);
	}

	@RequestMapping("/{category}")
	public String getProductsByCategory(Model model,
			@PathVariable("category") String category) {
		model.addAttribute("products",
				productService.getProductsByCategory(category));
		return "products";
	}

	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(
			@MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> filterParams,
			Model model) {
		model.addAttribute("products",
				productService.getProductsByFilter(filterParams));
		return "products";
	}

	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId,
			Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		return "product";
	}

	@RequestMapping("/{category}/{price}")
	public String filterProducts(
			@PathVariable("category") String category,
			@MatrixVariable(pathVar = "price") Map<String, List<String>> filterParams,
			@RequestParam("manufacturer") String manufacturer, Model model) {
		Set<Product> result = new HashSet<Product>();
		for (Product p : productService.getProductsByCategory(category)) {
			result.add(p);
		}
		
		if (manufacturer != null) {
			result.retainAll(productService
						.getProductsByManufacturer(manufacturer));
			
		}
		try {
			result.retainAll(productService.getProductsByPriceFilter(filterParams));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("products", result);
		return "products";

	}
	
	@RequestMapping(value="/add", method= RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		model.addAttribute("newProduct", new Product());
		return "addProduct";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
		productService.addProduct(newProduct);
		return "redirect:/products";
	}
}