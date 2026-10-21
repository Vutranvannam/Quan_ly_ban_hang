package com.example.qly_kho.service.application;

import com.example.qly_kho.dto.request.product.ProductCreateRequest;
import com.example.qly_kho.dto.request.product.ProductSearchRequest;
import com.example.qly_kho.dto.request.product.ProductUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.product.ProductResponse;

public interface ProductAppService {
    
    ProductResponse createProduct(ProductCreateRequest request, String ip, Long actorUserId);

    ProductResponse updateProduct(Long productId, ProductUpdateRequest request, String ip, Long actorUserId);

    void deleteProduct(Long productId, String ip, Long actorUserId);

    PageResponse<ProductResponse> getProductList(ProductSearchRequest request);

    ProductResponse getProduct(Long productId);

    byte[] excelTableExport(ProductSearchRequest request);
}
