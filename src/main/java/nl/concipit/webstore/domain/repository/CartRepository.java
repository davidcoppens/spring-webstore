package nl.concipit.webstore.domain.repository;

import nl.concipit.webstore.domain.Cart;

public interface CartRepository {
    Cart create(Cart cart);
    
    Cart read(String cartId);
    
    void update(String cartId, Cart cart);
    
    void delete(String cartId);
}
