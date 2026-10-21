package com.example.qly_kho.service.domain.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.repository.ProductBatchRepository;
import com.example.qly_kho.service.domain.ProductBatchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductBatchServiceImpl implements ProductBatchService {

    private final ProductBatchRepository productBatchRepository;
    
    @Override
    public void existsByProductId(Long productId) {
        
        if(productBatchRepository.existsByProduct_Id(productId)) {
            throw new DuplicateException(
                String.format("Product with id: %s already exists in ProductBatchService", productId)
            );
        }
    }
}
