package com.example.qly_kho.service.domain.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.ProductImage;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.repository.ProductImageRepository;
import com.example.qly_kho.service.domain.ProductImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    
    

    @Override
    public List<ProductImage> findByProductIdAndIsPrimaryIsTrue(Long productId) {
        return productImageRepository.findByProductIdAndIsPrimaryIsTrue(productId);
    }

    @Override
    public ProductImage saveProductImage(ProductImage productImage) {
        try{
            return productImageRepository.save(productImage);

        }catch(DataIntegrityViolationException e){
            throw new DuplicateException("Duplicate data");
        }
    }

    @Override
    public ProductImage updateProductImage(ProductImage productImage) {
        if(productImage.isPrimary()) {
            List<ProductImage> productImages = findByProductIdAndIsPrimaryIsTrue(productImage.getProduct().getId());
    
            for(ProductImage image : productImages) {
                image.changeToPrimary(false);
            }
        }
        
        return saveProductImage(productImage);
    }

    @Override
    public void deleteProductImage(ProductImage productImage) {
        
        productImageRepository.delete(productImage);
        productImageRepository.flush();
    }
}
