package com.example.qly_kho.dto.response.customer;

import java.time.LocalDateTime;

public record CustomerResponse(
    Long customerId,
    String name,
    String phone,
    String email,
    String address,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {}
