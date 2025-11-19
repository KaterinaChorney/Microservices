package com.flowerlabvlada.entities;

public record PaymentRequest(
        Long customerId,
        double amount
) {
}