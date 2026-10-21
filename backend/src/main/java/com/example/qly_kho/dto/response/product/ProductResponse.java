package com.example.qly_kho.dto.response.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.example.qly_kho.dto.response.productImage.ProductImageResponse;

public record ProductResponse(
    Long productId,
    String sku,
    String name,
    String description,
    String categoryName,
    String unitName,
    BigDecimal price,
    boolean active,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Set<ProductImageResponse> productImages
) {}
