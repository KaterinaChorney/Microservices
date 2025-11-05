package com.flowerlabvlada;

public record Customer(
        Long id,
        String name,
        String email,
        String address,
        String phone
) {
}