package com.example.qly_kho.service.domain;

public interface ImportOrderService {
    
    void existsBySupplierAndStatus(Long supplierId, String status);
}
