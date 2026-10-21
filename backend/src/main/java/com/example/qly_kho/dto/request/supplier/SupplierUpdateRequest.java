package com.example.qly_kho.dto.request.supplier;

public record SupplierUpdateRequest(

    String name,
    String phone,
    String email,
    String address
    
) {}
