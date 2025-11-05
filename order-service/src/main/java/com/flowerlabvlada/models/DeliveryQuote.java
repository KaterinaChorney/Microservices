package com.flowerlabvlada.models;

public record DeliveryQuote(
        String address,
        double price,
        String estimatedDeliveryDate
) {
}