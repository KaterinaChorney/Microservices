package com.flowerlabvlada;
import java.util.UUID;

public record PaymentResponse(
        String paymentId,
        String status,
        String message
)
{
    public static PaymentResponse approved(double amount) {
        return new PaymentResponse(
                UUID.randomUUID().toString(),
                "APPROVED",
                "Payment for " + amount + " UAH approved."
        );
    }
}