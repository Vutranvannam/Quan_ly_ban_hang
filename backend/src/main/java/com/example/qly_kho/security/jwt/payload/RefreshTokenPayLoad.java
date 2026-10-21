package com.example.qly_kho.security.jwt.payload;

public record RefreshTokenPayLoad(
    String username,
    Long authVersion
) {}
