package com.example.qly_kho.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank
    @Size(min = 4, max = 30)
    String username,

    @NotBlank
    @Size(min = 6, max = 100, message = "Password phải từ 6 đến 100 ký tự")
    String password,

    @NotBlank
    @Size(max = 100)
    String fullName,

    @NotBlank
    @Email
    String email
) {}