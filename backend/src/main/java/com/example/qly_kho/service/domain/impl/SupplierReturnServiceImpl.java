package com.example.qly_kho.service.domain.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.repository.SupplierReturnRepository;
import com.example.qly_kho.service.domain.SupplierReturnService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierReturnServiceImpl  implements SupplierReturnService {
    
    private final SupplierReturnRepository supplierReturnRepository;
    
    @Override
    public void existsBySupplierAndStatus(Long supplierId, String status) {
        
        if(supplierReturnRepository.existsBySupplier_IdAndStatus(supplierId, status)) {
            throw new DuplicateException("Supplier return with supplier " + supplierId + " and status " + status + " already exists");
        }
    }
}
