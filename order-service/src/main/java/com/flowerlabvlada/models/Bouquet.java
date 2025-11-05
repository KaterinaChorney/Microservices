package com.flowerlabvlada.models;

public record Bouquet(
        Long id,
        String name,
        double price,
        int stockQuantity
) {
}