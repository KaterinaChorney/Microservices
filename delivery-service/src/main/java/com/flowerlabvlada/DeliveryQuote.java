package com.flowerlabvlada;

public record DeliveryQuote(
        String address,
        double price,
        String estimatedDeliveryDate
) {
}