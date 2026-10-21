package com.example.qly_kho.service.domain.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.repository.ImportOrderRepository;
import com.example.qly_kho.service.domain.ImportOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImportOrderServiceImpl implements ImportOrderService {

    private final ImportOrderRepository importOrderRepository;
    
    @Override
    public void existsBySupplierAndStatus(Long supplierId, String status) {

        if(importOrderRepository.existsBySupplier_IdAndStatus(supplierId, status)) {
            throw new DuplicateException("Import order with supplier " + supplierId + " and status " + status + " already exists");
        }
    }
}
