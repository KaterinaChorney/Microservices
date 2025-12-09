package com.flowerlabvlada;

public record OrderCreatedEvent(
        Long orderId,
        Double totalAmount,
        String deliveryAddress
) {}