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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.qly_kho.dto.request.supplier.SupplierCreateRequest;
import com.example.qly_kho.dto.request.supplier.SupplierSreachRequest;
import com.example.qly_kho.dto.request.supplier.SupplierUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.supplier.SupplierResponse;
import com.example.qly_kho.security.cache.UserCache;
import com.example.qly_kho.service.application.SupplierAppService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierAppService supplierApplicationService;

    @PreAuthorize("hasAuthority('SUPPLIER_CREATE')")
    @PostMapping("/add")
    public ResponseEntity<Void> createSupplier(
        @RequestBody @Valid SupplierCreateRequest request, 
        HttpServletRequest HttpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {

        supplierApplicationService.createSupplier(
            request,  
            HttpServletRequest.getRemoteAddr(),
            user.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('SUPPLIER_UPDATE')")
    @PatchMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> updateSuppiler(
            @PathVariable Long supplierId, 
            @Valid @RequestBody SupplierUpdateRequest request, 
            HttpServletRequest httpServletRequest,
            @AuthenticationPrincipal UserCache user
    ) {

        return ResponseEntity.ok(
            supplierApplicationService.updateSuppiler(
                supplierId, 
                request, 
                httpServletRequest.getRemoteAddr(), 
                user.getId()
            )
        );
    }
    
    @PreAuthorize("hasAuthority('SUPPLIER_DELETE')")
    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(
        @PathVariable Long supplierId, 
        HttpServletRequest httpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {
        supplierApplicationService.deleteSupplier(
            supplierId, 
            httpServletRequest.getRemoteAddr(),
            user.getId()
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SUPPLIER_VIEW')")
    @GetMapping("/list")
    public PageResponse<SupplierResponse> getSupplierList(@Valid @ModelAttribute SupplierSreachRequest request) {

        return supplierApplicationService.getSupplierList(request);
    }

    @PreAuthorize("hasAuthority('SUPPLIER_VIEW')")
    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> getSupplier(@PathVariable Long supplierId) {

        return ResponseEntity.ok(supplierApplicationService.getSupplier(supplierId));
    }

    @PreAuthorize("hasAuthority('SUPPLIER_VIEW')")
    @GetMapping("/list/export-excel")
    public ResponseEntity<byte[]> excelTableExport(@Valid @ModelAttribute SupplierSreachRequest request) {
        byte[] file = supplierApplicationService.excelTableExport(request);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=categories.xlsx")
                .body(file);
    }
}
