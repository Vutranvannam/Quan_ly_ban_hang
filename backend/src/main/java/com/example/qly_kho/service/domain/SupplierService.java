package com.example.qly_kho.service.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.qly_kho.entity.Supplier;

public interface SupplierService {
    
    void existsByEmail(String email);

    void existsByPhone(String phone);

    void existsByAddress(String address);

    void existsByEmailAndIdNot(String email, Long id);

    void existsByPhoneAndIdNot(String phone, Long id);

    void existsByAddressAndIdNot(String address, Long id);

    Supplier getSupplierById(Long id);

    Supplier saveSupplier(Supplier supplier);

    Supplier deleteSupplier(Supplier supplier);

    Page<Supplier> searchSupplier(String keyword, Pageable pageable);

    List<Supplier> getAllForExport(String keyword);
}
