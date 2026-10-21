package com.example.qly_kho.service.application.impl;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.qly_kho.constant.ActionConstants;
import com.example.qly_kho.dto.request.supplier.SupplierCreateRequest;
import com.example.qly_kho.dto.request.supplier.SupplierSreachRequest;
import com.example.qly_kho.dto.request.supplier.SupplierUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.supplier.SupplierResponse;
import com.example.qly_kho.entity.Supplier;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.mapper.SupplierMapper;
import com.example.qly_kho.service.application.ExcelExportService;
import com.example.qly_kho.service.application.SupplierAppService;
import com.example.qly_kho.service.domain.ActivityLogService;
import com.example.qly_kho.service.domain.ImportOrderService;
import com.example.qly_kho.service.domain.SupplierReturnService;
import com.example.qly_kho.service.domain.SupplierService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SupplierAppServiceImpl implements SupplierAppService {

    private final SupplierService supplierService;
    private final ImportOrderService importOrderService;
    private final SupplierReturnService supplierReturnService;
    private final ActivityLogService activityLogService;
    private final SupplierMapper supplierMapper;
    private final ExcelExportService excelExportService;

    @Override
    public SupplierResponse createSupplier(SupplierCreateRequest request, String ip, Long actorUserId) {

        supplierService.existsByEmail(request.email());
        supplierService.existsByPhone(request.phone());
        supplierService.existsByAddress(request.address());

        Supplier supplier = Supplier.createSupplier(
            request.name(), 
            request.phone(), 
            request.email(), 
            request.address()
        );

        Supplier savedSupplier = supplierService.saveSupplier(supplier);
            
        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.CREATE, 
            ActionConstants.ENTITY_SUPPLIER, 
            savedSupplier.getId(), 
            "Supplier created successfully", 
            ip
        );
        
        return supplierMapper.toSupplierResponse(savedSupplier);
    }
    
    @Override
    public SupplierResponse updateSuppiler(Long supplierId, SupplierUpdateRequest request, String ip, Long actorUserId) {

        if(request.email() != null) supplierService.existsByEmailAndIdNot(request.email(), supplierId);
        if(request.phone() != null) supplierService.existsByPhoneAndIdNot(request.phone(), supplierId);
        if(request.address() != null) supplierService.existsByAddressAndIdNot(request.address(), supplierId);
        
        Supplier supplier = supplierService.getSupplierById(supplierId);

        if (supplier.getDeletedAt() != null) {
            throw new DuplicateException(
                String.format("Supplier with id %d has already been deleted", supplierId)
            );
        }

        if(request.name() != null) supplier.changeName(request.name());
        if(request.phone() != null) supplier.changePhone(request.phone());
        if(request.email() != null) supplier.changeEmail(request.email());
        if(request.address() != null) supplier.changeAddress(request.address());

        Supplier savedSupplier = supplierService.saveSupplier(supplier);
            
        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.UPDATE, 
            ActionConstants.ENTITY_SUPPLIER, 
            savedSupplier.getId(), 
            "Supplier updated successfully", 
            ip
        );
        
        return supplierMapper.toSupplierResponse(savedSupplier);
    }

    @Override
    public void deleteSupplier(Long supplierId, String ip, Long actorUserId) {
  
        Supplier supplier = supplierService.getSupplierById(supplierId);

        if (supplier.getDeletedAt() != null) {
            throw new DuplicateException(
                String.format("Supplier with id %d has already been deleted", supplierId)
            );
        }
        
        importOrderService.existsBySupplierAndStatus(supplierId, "APPROVED");
        supplierReturnService.existsBySupplierAndStatus(supplierId, "APPROVED");

        supplierService.deleteSupplier(supplier);
            
        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.DELETE, 
            ActionConstants.ENTITY_SUPPLIER, 
            supplierId, 
            "Supplier deleted successfully", 
            ip
        );
    }

    @Override
    public byte[] excelTableExport(SupplierSreachRequest request) {
        String keyword = request.keyword();

        List<Supplier> data = supplierService.getAllForExport(keyword);

        String[] headers = {"Tên", "SĐT", "Email", "Địa chiề", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"};

        List<Function<Supplier, Object>> fieldMappers = List.of(
            supplier -> supplier.getName(),
            supplier -> supplier.getPhone(),
            supplier -> supplier.getEmail(),
            supplier -> supplier.getAddress(),
            supplier -> supplier.getCreatedAt(),
            supplier -> supplier.getUpdatedAt(),
            supplier -> supplier.getDeletedAt()
        );

        return excelExportService.exportTableExcel(
            data,
            "supplier",
            "Danh sách Nhà cung cấp",
            headers,
            fieldMappers
        );
    }

    @Override
    public PageResponse<SupplierResponse> getSupplierList(SupplierSreachRequest request) {
        int page = request.page();
        int size = request.size();
        String keyword = request.keyword();

        PageRequest pageable = PageRequest.of(
            page, 
            size,
            Sort.by("deletedAt").descending()
        );

        Page<Supplier> supplierPage = supplierService.searchSupplier(keyword, pageable);

        List<SupplierResponse> data = supplierPage.getContent()
                                                .stream()
                                                .map(s -> supplierMapper.toSupplierResponse(s))
                                                .toList();

        return new PageResponse<>(
            data,
            supplierPage.getNumber(),
            supplierPage.getSize(),
            supplierPage.getTotalElements(),
            supplierPage.getTotalPages()
        );
    }

    @Override
    public SupplierResponse getSupplier(Long supplierId) {
       
        return supplierMapper.toSupplierResponse(supplierService.getSupplierById(supplierId));
    }

    
}
