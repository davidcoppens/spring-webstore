package nl.concipit.webstore.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.concipit.webstore.domain.Product;
import nl.concipit.webstore.exception.ProductNotFoundException;
import nl.concipit.webstore.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductIdValidator implements
        ConstraintValidator<ProductId, String> {

    @Autowired
    private ProductService productService;

    @Override
    public void initialize(ProductId constraintAnnotation) {
        // left blank

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Product product;
        try {
            product = productService.getProductById(value);
        } catch (ProductNotFoundException e) {
            return true;
        }
        if (product != null) {
            return false;
        }

        return true;
    }

}
