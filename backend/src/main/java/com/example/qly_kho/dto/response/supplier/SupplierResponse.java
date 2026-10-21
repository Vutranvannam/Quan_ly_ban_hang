package com.example.qly_kho.dto.response.supplier;

import java.time.LocalDateTime;

public record SupplierResponse(
    Long supplierId,
    String name,
    String phone,
    String email,
    String address,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {}
