package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.qly_kho.dto.response.supplier.SupplierResponse;
import com.example.qly_kho.entity.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    
    @Mapping(source = "id", target = "supplierId")
    SupplierResponse toSupplierResponse(Supplier supplier);
}
