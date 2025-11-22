package com.flowerlabvlada;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ApplicationScoped
public class DeliveryRepository implements PanacheRepository<DeliveryTariff> {

    public Optional<DeliveryQuote> calculateQuoteForAddress(String address) {
        if (address == null || address.isBlank()) {
            return Optional.empty();
        }

        Double price = listAll().stream()
                .filter(tariff -> address.toLowerCase().contains(tariff.getCityKeyword().toLowerCase()))
                .map(DeliveryTariff::getPrice)
                .findFirst()
                .orElse(350.00);

        String date = LocalDate.now().plusDays(1)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        DeliveryQuote quote = new DeliveryQuote(address, price, "Завтра, " + date);
        return Optional.of(quote);
    }
}