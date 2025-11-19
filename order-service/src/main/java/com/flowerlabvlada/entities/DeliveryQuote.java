package com.flowerlabvlada.entities;

public record DeliveryQuote(
        long orderId,
        String address,
        double price,
        String estimatedDeliveryDate
) {
}