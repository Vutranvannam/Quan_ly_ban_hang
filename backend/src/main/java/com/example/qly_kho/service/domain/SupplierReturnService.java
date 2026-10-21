package com.example.qly_kho.service.domain;

public interface SupplierReturnService {
    void existsBySupplierAndStatus(Long supplierId, String status);
}
