package com.example.qly_kho.security.jwt.payload;

public record AccessTokenPayload(
    String username,
    Long authVersion,
    Long permissionVersion
) {}
