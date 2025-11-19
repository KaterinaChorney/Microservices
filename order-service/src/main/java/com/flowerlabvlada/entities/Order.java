package com.flowerlabvlada.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "orders", schema = "orders")
public class Order extends PanacheEntity {
    public Long customerId;
    public Long bouquetId;
    public double totalAmount;
    public String deliveryAddress;
    public String paymentId;
    public String status;
    public LocalDate orderDate;

    public Order() {
    }

    public Order(Long customerId, Long bouquetId, double totalAmount,
                       String deliveryAddress, String paymentId, String status, LocalDate orderDate) {
        this.customerId = customerId;
        this.bouquetId = bouquetId;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.paymentId = paymentId;
        this.status = status;
        this.orderDate = orderDate;
    }
}