package com.flowerlabvlada;

import com.flowerlabvlada.models.Order;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class OrderRepository {

    private final AtomicLong ids = new AtomicLong(0);
    private final List<Order> orders = new CopyOnWriteArrayList<>();

    public Order save(Order order) {
        Order newOrder = new Order(
                ids.incrementAndGet(),
                order.customerId(),
                order.bouquetId(),
                order.totalAmount(),
                order.deliveryAddress(),
                order.paymentId(),
                order.status(),
                order.orderDate()
        );
        orders.add(newOrder);
        return newOrder;
    }

    public List<Order> listAll() {
        return Collections.unmodifiableList(orders);
    }
}