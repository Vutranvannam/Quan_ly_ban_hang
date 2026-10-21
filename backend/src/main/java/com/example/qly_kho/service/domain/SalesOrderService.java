package com.example.qly_kho.service.domain;

public interface SalesOrderService {
    
    void existsByCustomerAndStatus(Long customerId, String status);
}
