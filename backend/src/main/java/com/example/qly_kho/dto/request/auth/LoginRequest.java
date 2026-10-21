package com.example.qly_kho.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

    @JsonProperty("username")
    @NotBlank 
    @Size(max =255)
    String username,

    @JsonProperty("password")
    @NotBlank
    @Size(max = 255)
    String password
) {}
