package com.example.qly_kho.service.domain.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.repository.SalesOrderRepository;
import com.example.qly_kho.service.domain.SalesOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {
    
    private final SalesOrderRepository salesOrderRepository;

    @Override
    public void existsByCustomerAndStatus(Long customerId, String status) {
        
        if(salesOrderRepository.existsByCustomer_IdAndStatus(customerId, status)) {
            
            throw new DuplicateException("Sales order with customer " + customerId + " and status " + status + " already exists");
        }
    }
}
