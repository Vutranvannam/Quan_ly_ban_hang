package com.example.qly_kho.dto.response.category;

import java.time.LocalDateTime;

public record CategoryResponse(
    Long categoryId,
    String name,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {}
