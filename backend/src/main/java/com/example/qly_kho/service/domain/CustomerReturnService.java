package com.example.qly_kho.service.domain;

public interface CustomerReturnService {
    
    void existsByCustomerAndStatus(Long customerId, String status);
}
