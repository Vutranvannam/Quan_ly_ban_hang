package com.example.qly_kho.dto.request.customer;

public record CustomerUpdateRequest(
    
    String name,
    String phone,
    String email,
    String address
) {}
