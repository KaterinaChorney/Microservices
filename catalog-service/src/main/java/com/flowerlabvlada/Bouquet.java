package com.flowerlabvlada;

public record Bouquet(
        Long id,
        String name,
        String description,
        double price,
        int stockQuantity
) {
}