package com.flowerlabvlada;

public record PaymentRequest(
        Long customerId,
        double amount
) {
}