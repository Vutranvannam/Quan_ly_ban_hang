package com.example.qly_kho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qly_kho.entity.SupplierReturn;

@Repository
public interface SupplierReturnRepository  extends JpaRepository<SupplierReturn, Long> {
    
    boolean existsBySupplier_IdAndStatus(Long supplierId, String status);
}
