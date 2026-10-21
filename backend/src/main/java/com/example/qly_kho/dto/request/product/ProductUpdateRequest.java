package com.example.qly_kho.dto.request.product;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public record ProductUpdateRequest(
    String name,
    Long categoryId,
    String unit,
    BigDecimal price,
    Boolean active,
    String description,
    MultipartFile image
) {}
