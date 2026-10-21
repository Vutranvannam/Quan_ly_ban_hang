package com.example.qly_kho.dto.response.unit;

import java.time.LocalDateTime;

public record UnitResponse(
    Long unitId,
    String name,
    LocalDateTime createdAt
) {}
