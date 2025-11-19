package com.flowerlabvlada.entities;

public record Bouquet(
        Long id,
        String name,
        String description,
        double price,
        int stockQuantity
) {
}