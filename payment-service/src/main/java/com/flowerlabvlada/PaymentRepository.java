package com.flowerlabvlada;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class PaymentRepository {

    public Optional<PaymentResponse> authorizePayment(PaymentRequest request) {
        if (request.amount() <= 0) {
            return Optional.empty();
        }

        return Optional.of(PaymentResponse.approved(request.amount()));
    }
}