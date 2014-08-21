package nl.concipit.webstore.service.impl;

import nl.concipit.webstore.domain.Cart;
import nl.concipit.webstore.domain.repository.CartRepository;
import nl.concipit.webstore.exception.InvalidCartException;
import nl.concipit.webstore.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Override
    public Cart create(Cart cart) {
        return cartRepository.create(cart);
    }

    @Override
    public Cart read(String cartId) {
        return cartRepository.read(cartId);
    }

    @Override
    public void update(String cartId, Cart cart) {
        cartRepository.update(cartId, cart);
    }

    @Override
    public void delete(String cartId) {
        cartRepository.delete(cartId);
    }

    @Override
    public Cart validate(String cartId) {
        Cart cart = cartRepository.read(cartId);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new InvalidCartException(cartId);
        }
        
        return cart;
    }

}
