package com.flowerlabvlada.entities;

public record PaymentResponse(
        String paymentId,
        String status,
        String message
) {
}