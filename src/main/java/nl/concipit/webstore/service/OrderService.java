package nl.concipit.webstore.service;

public interface OrderService {
	void processOrder(String productId, int quantity);
}
