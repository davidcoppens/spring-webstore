package nl.concipit.webstore.service.impl;

import nl.concipit.webstore.domain.Order;
import nl.concipit.webstore.domain.Product;
import nl.concipit.webstore.domain.repository.OrderRepository;
import nl.concipit.webstore.domain.repository.ProductRepository;
import nl.concipit.webstore.service.CartService;
import nl.concipit.webstore.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public void processOrder(String productId, int quantity) {
		Product product = repository.getProductById(productId);
		
		if (product.getUnitsInStock() < quantity) {
			throw new IllegalArgumentException("Not enough in stock. Available: " + product.getUnitsInStock());
			
		} 
		product.setUnitsInStock(product.getUnitsInStock() - quantity);
	}

    @Override
    public Long saveOrder(Order order) {
        Long orderId = orderRepository.saveOrder(order);
        cartService.delete(order.getCart().getCartId());
        return orderId;
    }

}
