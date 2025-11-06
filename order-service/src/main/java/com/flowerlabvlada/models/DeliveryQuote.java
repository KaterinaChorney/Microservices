package com.flowerlabvlada.models;

public record DeliveryQuote(
        long orderId,
        String address,
        double price,
        String estimatedDeliveryDate
) {
}