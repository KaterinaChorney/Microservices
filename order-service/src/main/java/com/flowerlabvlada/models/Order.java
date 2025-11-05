package com.flowerlabvlada.models;

import java.time.LocalDate;

public record Order(
        Long id,
        Long customerId,
        Long bouquetId,
        double totalAmount,
        String deliveryAddress,
        String paymentId,
        String status,
        LocalDate orderDate
) {
}