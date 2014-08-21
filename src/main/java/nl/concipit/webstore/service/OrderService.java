package nl.concipit.webstore.service;

import nl.concipit.webstore.domain.Order;

public interface OrderService {
	void processOrder(String productId, int quantity);
	Long saveOrder(Order order);
}
