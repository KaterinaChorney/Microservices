package com.flowerlabvlada.entities;

public record OrderRequest(
        Long customerId,
        Long bouquetId
) {
}