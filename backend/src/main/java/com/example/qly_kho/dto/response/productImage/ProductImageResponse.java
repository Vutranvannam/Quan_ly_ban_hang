package com.example.qly_kho.dto.response.productImage;

public record ProductImageResponse(
    Long productImageId,
    String imageUrl,
    boolean isPrimary,
    Integer sortOrder,
    String altText
) {}
