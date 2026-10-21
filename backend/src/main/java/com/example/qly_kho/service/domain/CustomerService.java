package com.example.qly_kho.service.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.qly_kho.entity.Customer;

public interface CustomerService {

    void existsByEmail(String email);

    void existsByPhone(String phone);

    void existsByAddress(String address);

    void existsByEmailAndIdNot(String email, Long id);

    void existsByPhoneAndIdNot(String phone, Long id);

    void existsByAddressAndIdNot(String address, Long id);

    Customer getCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    Customer deleteCustomer(Customer customer);

    Page<Customer> searchCustomer(String keyword, Pageable pageable);

    List<Customer> getAllForExport(String keyword);
}
