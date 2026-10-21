package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.qly_kho.dto.response.product.ProductResponse;
import com.example.qly_kho.entity.Product;

@Mapper(
    componentModel = "spring",
    uses = ProductImageMapper.class
)
public interface ProductMapper {

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "sellingPrice", target = "price")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "unit.name", target = "unitName")
    @Mapping(source = "productImages", target = "productImages")
    ProductResponse toProductResponse(Product product);
}
