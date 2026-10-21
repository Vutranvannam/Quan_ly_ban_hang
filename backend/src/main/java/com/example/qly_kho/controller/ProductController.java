package com.example.qly_kho.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.qly_kho.dto.request.product.ProductCreateRequest;
import com.example.qly_kho.dto.request.product.ProductSearchRequest;
import com.example.qly_kho.dto.request.product.ProductUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.product.ProductResponse;
import com.example.qly_kho.security.cache.UserCache;
import com.example.qly_kho.service.application.ProductAppService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductAppService productAppService;

    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @PostMapping("/add")
    public ResponseEntity<Void> createProduct(
        @Valid @ModelAttribute ProductCreateRequest request,
        HttpServletRequest httpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {

        productAppService.createProduct(
            request, 
            httpServletRequest.getRemoteAddr(),
            user.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId, 
            @Valid @ModelAttribute ProductUpdateRequest request, 
            HttpServletRequest httpServletRequest,
            @AuthenticationPrincipal UserCache user
    ) {

        return ResponseEntity.ok(
            productAppService.updateProduct(
                productId, 
                request, 
                httpServletRequest.getRemoteAddr(), 
                user.getId()
            )
        );
    }
    
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
        @PathVariable Long productId, 
        HttpServletRequest httpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {
        productAppService.deleteProduct(
            productId, 
            httpServletRequest.getRemoteAddr(),
            user.getId()
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/list")
    public ResponseEntity<PageResponse<ProductResponse>> getProductList(@Valid @ModelAttribute ProductSearchRequest request) {
        
        return ResponseEntity.ok(productAppService.getProductList(request));
    }

    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {

        return ResponseEntity.ok(productAppService.getProduct(productId));
    }

    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/list/export-excel")
    public ResponseEntity<byte[]> excelTableExport(@Valid @ModelAttribute ProductSearchRequest request) {

         byte[] file = productAppService.excelTableExport(request);

         return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=categories.xlsx")
                .body(file);
    }
}
