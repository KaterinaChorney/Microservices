package com.flowerlabvlada.models;

public record PaymentRequest(
        Long customerId,
        double amount
) {
}