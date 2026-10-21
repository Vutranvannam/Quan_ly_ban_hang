package com.example.qly_kho.dto.request.user;

import java.util.List;

public record UserUpdateRequest(
    String fullName,
    String email,
    List<Long> roleIds,
    Boolean status,
    Boolean locked
) {}
