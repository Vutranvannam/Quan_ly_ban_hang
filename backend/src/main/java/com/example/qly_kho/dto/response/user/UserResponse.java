package com.example.qly_kho.dto.response.user;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
    Long userId,
    String username,
    String fullName,
    String email,
    Set<String> roles,
    boolean enabled,
    boolean locked,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {}
