package com.example.qly_kho.dto.request.customer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CustomerSreachRequest(
    @Min(value = 0, message = "Page phải >= 0")
    int page,

    @Min(value = 1, message = "Size phải >= 1")
    @Max(value = 20, message = "Size tối đa là 20")
    int size,

    String keyword
) {}
