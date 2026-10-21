package com.example.qly_kho.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(

    @NotBlank
    @Size(min = 3, max = 255)
    String name,

    @NotBlank
    String description
) {}
