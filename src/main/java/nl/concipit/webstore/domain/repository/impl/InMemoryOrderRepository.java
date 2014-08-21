package nl.concipit.webstore.domain.repository.impl;

import java.util.HashMap;
import java.util.Map;

import nl.concipit.webstore.domain.Order;
import nl.concipit.webstore.domain.repository.OrderRepository;

public class InMemoryOrderRepository implements OrderRepository {

    private Map<Long, Order> listOfOrders;
    private long nextOrderId;

    public InMemoryOrderRepository() {
        listOfOrders = new HashMap<Long, Order>();
        nextOrderId = 1000;
    }

    @Override
    public Long saveOrder(Order order) {
        order.setOrderId(getNextOrderId());
        listOfOrders.put(order.getOrderId(), order);
        return order.getOrderId();
    }

    private synchronized long getNextOrderId() {
        return nextOrderId++;
    }

}
