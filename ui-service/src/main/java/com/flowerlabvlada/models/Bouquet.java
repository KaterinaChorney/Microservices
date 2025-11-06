package com.flowerlabvlada.models;

public record Bouquet(
        long id,
        String name,
        String description,
        double price,
        int stockQuantity
) {
}