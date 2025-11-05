package com.flowerlabvlada;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ApplicationScoped
public class DeliveryRepository {

    public Optional<DeliveryQuote> getQuoteForAddress(String address) {
        if (address == null || address.isBlank()) {
            return Optional.empty();
        }

        double price;
        if (address.toLowerCase().contains("чернівці")) {
            price = 200.00;
        } else {
            price = 350.00;
        }

        String date = LocalDate.now().plusDays(1)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        DeliveryQuote quote = new DeliveryQuote(address, price, "Завтра, " + date);
        return Optional.of(quote);
    }
}
