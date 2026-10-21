package com.example.qly_kho.service.application;

import com.example.qly_kho.dto.request.supplier.SupplierCreateRequest;
import com.example.qly_kho.dto.request.supplier.SupplierSreachRequest;
import com.example.qly_kho.dto.request.supplier.SupplierUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.supplier.SupplierResponse;

public interface SupplierAppService {
    
    SupplierResponse createSupplier(SupplierCreateRequest request,  String ip, Long actorUserId);

    SupplierResponse updateSuppiler(Long supplierId, SupplierUpdateRequest request, String ip, Long actorUserId);

    void deleteSupplier(Long supplierId, String ip, Long actorUserId);
    
    PageResponse<SupplierResponse> getSupplierList(SupplierSreachRequest request);

    SupplierResponse getSupplier(Long supplierId);

    byte[] excelTableExport(SupplierSreachRequest request);
}
