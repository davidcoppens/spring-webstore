package nl.concipit.webstore.domain.repository;

import nl.concipit.webstore.domain.Order;

public interface OrderRepository {
    Long saveOrder(Order order);
}
