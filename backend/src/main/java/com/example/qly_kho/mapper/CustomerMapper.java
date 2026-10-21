package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.qly_kho.dto.response.customer.CustomerResponse;
import com.example.qly_kho.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    @Mapping(source = "id", target = "customerId")
    CustomerResponse toCustomerResponse(Customer customer);
}
