package com.flowerlabvlada;

import com.flowerlabvlada.entities.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public Order save(Order order) {
        persist(order);
        return order;
    }
}