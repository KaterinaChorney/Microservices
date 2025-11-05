package com.flowerlabvlada.models;

public record PaymentResponse(
        String paymentId,
        String status,
        String message
) {
}