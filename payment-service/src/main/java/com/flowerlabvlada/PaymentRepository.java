package com.flowerlabvlada;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<PaymentTransaction> {

    public Optional<PaymentResponse> authorizePayment(PaymentRequest request) {
        if (request.amount() <= 0) {
            return Optional.empty();
        }

        String transactionId = UUID.randomUUID().toString();
        String status = "APPROVED";

        PaymentTransaction transaction = new PaymentTransaction(
                request.customerId(),
                request.amount(),
                status,
                transactionId
        );

        persist(transaction);

        return Optional.of(new PaymentResponse(
                transactionId,
                status,
                "Payment for " + request.amount() + " UAH approved."
        ));
    }
}