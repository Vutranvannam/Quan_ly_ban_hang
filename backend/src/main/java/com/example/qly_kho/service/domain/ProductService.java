package com.example.qly_kho.service.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.qly_kho.entity.Product;

public interface ProductService {

    void existsBySku(String sku);

    void existsByCategoryIdAndDeletedAtIsNull(Long categoryId);

    Page<Product> searchProduct(String keyword, Pageable pageable);

    Product findProductById(Long productId);

    Product saveProduct(Product product);

    Product updateProduct(Product product);
    
    Product deleteProduct(Product product);
    
    List<Product> getAllForExport(String keyword);
}
