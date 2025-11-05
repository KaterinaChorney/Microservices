package com.flowerlabvlada.models;

public record OrderRequest(
        Long customerId,
        Long bouquetId
) {
}