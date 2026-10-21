package com.example.qly_kho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qly_kho.entity.ProductBatch;

@Repository
public interface ProductBatchRepository extends JpaRepository<ProductBatch, Long> {
    
    boolean existsByProduct_Id(Long productId);
}
