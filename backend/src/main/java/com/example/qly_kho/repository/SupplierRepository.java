package com.example.qly_kho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.qly_kho.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByAddress(String address);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByAddressAndIdNot(String address, Long id);
}
