package com.example.qly_kho.service.domain.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.Customer;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.repository.CustomerRepository;
import com.example.qly_kho.service.domain.CustomerService;
import com.example.qly_kho.specification.CustomerSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    @Override
    public void existsByEmail(String email) {
        if(customerRepository.existsByEmail(email)) {
            throw new DuplicateException(
                String.format("Customer with email: %s already exists", email)
            );
        }
    }

    @Override
    public void existsByPhone(String phone) {
        if(customerRepository.existsByPhone(phone)) {
            throw new DuplicateException(
                String.format("Customer with phone: %s already exists", phone)
            );
        }
    }

    @Override
    public void existsByAddress(String address) {
       if(customerRepository.existsByAddress(address)) {
            throw new DuplicateException(
                String.format("Customer with address: %s already exists", address)
            );
        }
    }

    @Override
    public void existsByAddressAndIdNot(String address, Long id) {
        if(customerRepository.existsByAddressAndIdNot(address, id)) {
            throw new DuplicateException(
                String.format("Customer with address: %s already exists", address)
            );
        }
    }

    @Override
    public void existsByEmailAndIdNot(String email, Long id) {
        if(customerRepository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateException(
                String.format("Customer with email: %s already exists", email)
            );
        }
    }

    @Override
    public void existsByPhoneAndIdNot(String phone, Long id) {
        if(customerRepository.existsByPhoneAndIdNot(phone, id)) {
            throw new DuplicateException(
                String.format("Customer with phone: %s already exists", phone)
            );
        }
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                String.format("Customer with (id: %s) not found in CustomerService", id)
            ));
    }

    @Override
    public Customer saveCustomer(Customer customer) {

        try {
            return customerRepository.save(customer);
        } catch (Exception e) {

            if (e.getMessage().contains("uk_customers_email")) {
                throw new DuplicateException("Email already exists in CustomerService");
            }

            if (e.getMessage().contains("uk_customers_phone")) {
                throw new DuplicateException("Phone already exists in CustomerService");
            }

            throw new DuplicateException("Duplicate data");
        }
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        
        customer.deleteCustomer();
        return customerRepository.save(customer);
    }

    @Override
    public Page<Customer> searchCustomer(String keyword, Pageable pageable) {
        Specification<Customer> specification = Specification
                                                    .where(CustomerSpecification.hasName(keyword))
                                                    .or(CustomerSpecification.hasEmail(keyword))
                                                    .or(CustomerSpecification.hasPhone(keyword))
                                                    .or(CustomerSpecification.hasAddress(keyword));
        return customerRepository.findAll(specification, pageable);
    }

    @Override
    public List<Customer> getAllForExport(String keyword) {
        Specification<Customer> specification = Specification
                                                    .where(CustomerSpecification.hasName(keyword))
                                                    .or(CustomerSpecification.hasEmail(keyword))
                                                    .or(CustomerSpecification.hasPhone(keyword))
                                                    .or(CustomerSpecification.hasAddress(keyword));
        return customerRepository.findAll(specification);
    }
}
