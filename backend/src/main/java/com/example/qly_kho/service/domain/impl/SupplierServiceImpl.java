package com.example.qly_kho.service.domain.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.Supplier;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.repository.SupplierRepository;
import com.example.qly_kho.service.domain.SupplierService;
import com.example.qly_kho.specification.SupplierSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    @Override
    public void existsByEmail(String email) {
        if (supplierRepository.existsByEmail(email)) {
            throw new DuplicateException(
                String.format("Supplier with email: %s already exists", email)
            );
        }
    }

    @Override
    public void existsByPhone(String phone) {
        if (supplierRepository.existsByPhone(phone)) {
            throw new DuplicateException(
                String.format("Supplier with phone: %s already exists", phone)
            );
        }
        
    }

    @Override
    public void existsByAddress(String address) {
        if(supplierRepository.existsByAddress(address)) {
            throw new DuplicateException(
                String.format("Supplier with address: %s already exists", address)
            );
        }
    }

    @Override
    public void existsByEmailAndIdNot(String email, Long id) {
        if(supplierRepository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateException(
                String.format("Supplier with email: %s already exists", email)
            );
        }
    }

    @Override
    public void existsByPhoneAndIdNot(String phone, Long id) {
        if(supplierRepository.existsByPhoneAndIdNot(phone, id)) {
            throw new DuplicateException(
                String.format("Supplier with phone: %s already exists", phone)
            );
        }
    }

    @Override
    public void existsByAddressAndIdNot(String address, Long id) {
        if(supplierRepository.existsByAddressAndIdNot(address, id)) {
            throw new DuplicateException(
                String.format("Supplier with address: %s already exists", address)
            );
        }
    }

    @Override
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                String.format("Supplier with (id: %s) not found in SupplierService", id)
            ));
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {

        try {
            return supplierRepository.save(supplier);
        } catch (Exception e) {

            if (e.getMessage().contains("uk_suppliers_email")) {
                throw new DuplicateException("Email already exists in supplierService");
            }

            if (e.getMessage().contains("uk_suppliers_phone")) {
                throw new DuplicateException("Phone already exists in supplierService");
            }

            throw new DuplicateException("Duplicate data");
        }
    }

    @Override
    public Supplier deleteSupplier(Supplier supplier) {
        
        supplier.deleteSupplier();
        
        return supplierRepository.save(supplier);
    }

    @Override
    public Page<Supplier> searchSupplier(String keyword, Pageable pageable) {
        Specification<Supplier> specification = Specification
                                                    .where(SupplierSpecification.hasName(keyword))
                                                    .or(SupplierSpecification.hasEmail(keyword))
                                                    .or(SupplierSpecification.hasPhone(keyword))
                                                    .or(SupplierSpecification.hasAddress(keyword));
        return supplierRepository.findAll(specification, pageable);
    }
    
    @Override
    public List<Supplier> getAllForExport(String keyword) {
        Specification<Supplier> specification = Specification
                                                    .where(SupplierSpecification.hasName(keyword))
                                                    .or(SupplierSpecification.hasEmail(keyword))
                                                    .or(SupplierSpecification.hasPhone(keyword))
                                                    .or(SupplierSpecification.hasAddress(keyword));
        return supplierRepository.findAll(specification);
    }

}
