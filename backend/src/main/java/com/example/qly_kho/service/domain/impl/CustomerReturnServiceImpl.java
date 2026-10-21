package com.example.qly_kho.service.domain.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.repository.CustomerReturnRepository;
import com.example.qly_kho.service.domain.CustomerReturnService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerReturnServiceImpl implements CustomerReturnService {
    
    private final CustomerReturnRepository customerReturnRepository;

    @Override
    public void existsByCustomerAndStatus(Long customerId, String status) {
        
        if(customerReturnRepository.existsBySalesOrder_Customer_IdAndStatus(customerId, status)) {
            throw new DuplicateException("Customer return with customer " + customerId + " and status " + status + " already exists");
        }
    }
}
