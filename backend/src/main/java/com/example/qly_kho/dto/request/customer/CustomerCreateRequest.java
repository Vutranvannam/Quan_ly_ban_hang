package com.example.qly_kho.dto.request.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerCreateRequest(

    @NotBlank
    String name,

    @NotBlank
    @Pattern(regexp = "^0\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    String phone,

    @NotBlank
    @Email(message = "Email không hợp lệ")
    String email,

    @NotBlank
    String address
) {}
