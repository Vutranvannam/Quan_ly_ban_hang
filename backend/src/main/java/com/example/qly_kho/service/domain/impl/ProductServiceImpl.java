package com.example.qly_kho.service.domain.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.Product;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.repository.ProductRepository;
import com.example.qly_kho.service.domain.ProductService;
import com.example.qly_kho.specification.ProductSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{
    
    private final ProductRepository productRepository;
    
    @Override
    public void existsBySku(String sku) {
        if(productRepository.existsBySku(sku)) {
            throw new DuplicateException(
                String.format("Product with sku: %s already exists in ProductService", sku)
            );
        }
    }

    @Override
    public void existsByCategoryIdAndDeletedAtIsNull(Long categoryId) {
       
       if(productRepository.existsByCategory_IdAndDeletedAtIsNull(categoryId)) {
           throw new DuplicateException(
               String.format("Category with id: %s already exists in ProductService", categoryId)
           );
       } 
    }

    @Override
    public Page<Product> searchProduct(String keyword, Pageable pageable) {
        Specification<Product> specification = Specification
                                                    .where(ProductSpecification.hasName(keyword))
                                                    .or(ProductSpecification.hasSku(keyword))
                                                    .or(ProductSpecification.hasNameCatgory(keyword))
                                                    .or(ProductSpecification.hasUnitName(keyword));
        return productRepository.findAll(specification, pageable);
    }
    
    
    @Override
    public Product findProductById(Long productId) {
       
        return productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(
                String.format("Product with id: %s not found in ProductService", productId)
            ));
    }

    @Override
    public Product saveProduct(Product product) {

        try {

            return productRepository.save(product);
            
        } catch (DataIntegrityViolationException e) {

            if (e.getMessage().contains("uk_product_sku")) {
                throw new DuplicateException("Sku already exists in ProductService");
            }

            throw new DuplicateException("Duplicate data");
        }
    }

    @Override
    public Product updateProduct(Product product) {
        
        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Product product) {
        
        product.deleteProduct();
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllForExport(String keyword) {
        Specification<Product> specification = Specification
                                                    .where(ProductSpecification.hasName(keyword))
                                                    .or(ProductSpecification.hasSku(keyword))
                                                    .or(ProductSpecification.hasNameCatgory(keyword))
                                                    .or(ProductSpecification.hasUnitName(keyword));
        return productRepository.findAll(specification);
    }

    
}
