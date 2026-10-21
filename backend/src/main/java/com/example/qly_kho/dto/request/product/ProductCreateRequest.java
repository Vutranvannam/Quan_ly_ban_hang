package com.example.qly_kho.dto.request.product;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateRequest(
    @NotBlank(message = "SKU không được để trống")
    String sku,

    @NotBlank(message = "Tên sản phẩm không được để trống")
    String name,

    @NotNull(message = "Category không được để trống")
    Long categoryId,

    @NotBlank(message = "Đơn vị không được để trống")
    String unit,

    @PositiveOrZero(message = "Giá phải >= 0")
    BigDecimal price,

    @NotNull(message = "Trạng thái không được để trống")
    Integer status,

    String description,

    MultipartFile image
) {}
