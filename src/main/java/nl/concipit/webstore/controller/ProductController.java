package nl.concipit.webstore.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import nl.concipit.webstore.domain.Product;
import nl.concipit.webstore.exception.NoProductsFoundUnderCategoryException;
import nl.concipit.webstore.exception.ProductNotFoundException;
import nl.concipit.webstore.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@InitBinder
	public void initialiseBinding(WebDataBinder binder) {
		binder.setDisallowedFields("discontinued", "unitsInOrder");
		binder.setAllowedFields("name", "productId", "unitPrice",
				"description", "manufacturer", "category", "unitsInStock",
				"productImage", "condition","language");
	}

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
		List<Product> products = productService.getProductsByCategory(category);

		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}

		model.addAttribute("products", products);
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
			result.retainAll(productService
					.getProductsByPriceFilter(filterParams));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		model.addAttribute("products", result);
		return "products";

	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		model.addAttribute("newProduct", new Product());
		return "addProduct";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewProductForm(
			@ModelAttribute("newProduct") @Valid Product newProduct,
			BindingResult result, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			return "addProduct";
		}
		
		String[] suppressedFields = result.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: "
					+ StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}

		MultipartFile productImage = newProduct.getProductImage();
		String rootDirectory = request.getSession().getServletContext()
				.getRealPath("/");

		if (productImage != null && !productImage.isEmpty()) {

			try {
				productImage.transferTo(new File(rootDirectory
						+ "\\resources\\images\\" + newProduct.getProductId()
						+ ".png"));
			} catch (IllegalStateException | IOException e) {
				throw new RuntimeException("Product image saving failed", e);
			}

		}

		productService.addProduct(newProduct);
		return "redirect:/products";
	}
	
	@RequestMapping("/invalidPromoCode")
	public String invalidPromoCode() {
		return "invalidPromoCode";
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
		mav.setViewName("productNotFound");
		return mav;
	}
}