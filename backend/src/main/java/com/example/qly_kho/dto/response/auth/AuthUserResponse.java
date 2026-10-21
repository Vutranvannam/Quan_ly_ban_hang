package com.example.qly_kho.dto.response.auth;

import java.util.Set;

public record AuthUserResponse(
    Long userId,
    String username,
    String fullName,
    String email,
    Set<String> roles
) {}
