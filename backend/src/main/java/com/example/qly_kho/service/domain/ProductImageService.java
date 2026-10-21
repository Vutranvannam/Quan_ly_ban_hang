package com.example.qly_kho.service.domain;

import java.util.List;

import com.example.qly_kho.entity.ProductImage;

public interface ProductImageService {

    List<ProductImage> findByProductIdAndIsPrimaryIsTrue(Long productId);

    ProductImage saveProductImage(ProductImage productImage);

    ProductImage updateProductImage(ProductImage productImage);

    void deleteProductImage(ProductImage productImage);
}
