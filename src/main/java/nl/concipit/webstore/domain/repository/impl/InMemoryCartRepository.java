package nl.concipit.webstore.domain.repository.impl;

import java.util.HashMap;
import java.util.Map;

import nl.concipit.webstore.domain.Cart;
import nl.concipit.webstore.domain.repository.CartRepository;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCartRepository implements CartRepository {

    private Map<String, Cart> listOfCarts;

    public InMemoryCartRepository() {
        listOfCarts = new HashMap<String, Cart>();
    }

    @Override
    public Cart create(Cart cart) {
        if (listOfCarts.keySet().contains(cart.getCartId())) {
            throw new IllegalArgumentException(
                    String.format(
                            "Cannot create a cart. Cart with the given ID [%s] alread exists",
                            cart.getCartId()));

        }
        listOfCarts.put(cart.getCartId(), cart);
        return cart;
    }

    @Override
    public Cart read(String cartId) {
        return listOfCarts.get(cartId);
    }

    @Override
    public void update(String cartId, Cart cart) {
        if (!listOfCarts.keySet().contains(cartId)) {
            throw new IllegalArgumentException(String.format(
                    "Cannot update cart. The cart with ID [%s] does not exist",
                    cartId));
        }
        listOfCarts.put(cartId, cart);
    }

    @Override
    public void delete(String cartId) {
        if (!listOfCarts.keySet().contains(cartId)) {
            throw new IllegalArgumentException(String.format(
                    "Cannot delete cart. The cart with ID [%s] does not exist",
                    cartId));
        }
        listOfCarts.remove(cartId);
    }
}
