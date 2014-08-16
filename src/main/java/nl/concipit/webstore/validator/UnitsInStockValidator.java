package nl.concipit.webstore.validator;

import java.math.BigDecimal;

import nl.concipit.webstore.domain.Product;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UnitsInStockValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        if (product.getUnitPrice() != null
                && new BigDecimal(10000).compareTo(product.getUnitPrice()) <= 0
                && product.getUnitsInStock() > 99) {
            errors.rejectValue("unitsInStock", "nl.concipit.webstore.validator.UnitsInStock.message");
        }
    }
}
