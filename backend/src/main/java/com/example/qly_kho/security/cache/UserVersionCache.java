package com.example.qly_kho.security.cache;

public record UserVersionCache(
    String username,
    Long authVersion,
    Long permissionVersion
) {} 