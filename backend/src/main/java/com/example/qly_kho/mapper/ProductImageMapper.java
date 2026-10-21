package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.qly_kho.dto.response.productImage.ProductImageResponse;
import com.example.qly_kho.entity.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(source = "id", target = "productImageId")
    @Mapping(source = "primary", target = "isPrimary")
    ProductImageResponse toProductImageResponse(ProductImage image);
}
