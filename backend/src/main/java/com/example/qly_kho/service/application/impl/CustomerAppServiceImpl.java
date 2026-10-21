package com.example.qly_kho.service.application.impl;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.qly_kho.constant.ActionConstants;
import com.example.qly_kho.dto.request.customer.CustomerCreateRequest;
import com.example.qly_kho.dto.request.customer.CustomerSreachRequest;
import com.example.qly_kho.dto.request.customer.CustomerUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.customer.CustomerResponse;
import com.example.qly_kho.entity.Customer;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.mapper.CustomerMapper;
import com.example.qly_kho.service.application.CustomerAppService;
import com.example.qly_kho.service.application.ExcelExportService;
import com.example.qly_kho.service.domain.ActivityLogService;
import com.example.qly_kho.service.domain.CustomerReturnService;
import com.example.qly_kho.service.domain.CustomerService;
import com.example.qly_kho.service.domain.SalesOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerAppServiceImpl implements CustomerAppService{

    private final CustomerService customerService;
    private final SalesOrderService salesOrderService;
    private final CustomerReturnService customerReturnService;
    private final ActivityLogService activityLogService;
    private final CustomerMapper customerMapper;
    private final ExcelExportService excelExportService;
    
    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request, String ip, Long actorUserId) {

        customerService.existsByEmail(request.email());
        customerService.existsByPhone(request.phone());
        customerService.existsByAddress(request.address());

        Customer customer = Customer.createCustomer(
            request.name(), 
            request.phone(), 
            request.email(), 
            request.address()
        );

        Customer savedCustomer = customerService.saveCustomer(customer);

        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.CREATE, 
            ActionConstants.ENTITY_CUSTOMER, 
            savedCustomer.getId(), 
            "Customer created successfully", 
            ip
        );

        return customerMapper.toCustomerResponse(savedCustomer);
    }

    @Override
    public CustomerResponse updateCustomer(Long customerId, CustomerUpdateRequest request, String ip, Long actorUserId) {

        if(request.email() != null) customerService.existsByEmailAndIdNot(request.email(), customerId);
        if(request.phone() != null) customerService.existsByPhoneAndIdNot(request.phone(), customerId);
        if(request.address() != null) customerService.existsByAddressAndIdNot(request.address(), customerId);

        Customer customer = customerService.getCustomerById(customerId);

        if (customer.getDeletedAt() != null) {
            throw new DuplicateException(
                String.format("Customer with id %d has already been deleted", customerId)
            );
        }

        if(request.name() != null) customer.changeName(request.name());
        if(request.phone() != null) customer.changePhone(request.phone());
        if(request.email() != null) customer.changeEmail(request.email());
        if(request.address() != null) customer.changeAddress(request.address());

        Customer savedCustomer = customerService.saveCustomer(customer);
        
        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.UPDATE, 
            ActionConstants.ENTITY_CUSTOMER, 
            savedCustomer.getId(), 
            "Customer updated successfully", 
            ip
        );

        return customerMapper.toCustomerResponse(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId, String ip, Long actorUserId) {

        Customer customer = customerService.getCustomerById(customerId);

        if (customer.getDeletedAt() != null) {
            throw new DuplicateException(
                String.format("Customer with id %d has already been deleted", customerId)
            );
        }

        salesOrderService.existsByCustomerAndStatus(customerId, "APPROVED");
        customerReturnService.existsByCustomerAndStatus(customerId, "APPROVED");

        customerService.deleteCustomer(customer);

        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.DELETE, 
            ActionConstants.ENTITY_CUSTOMER, 
            customerId, 
            "Customer deleted successfully", 
            ip
        );
    }

    @Override
    public PageResponse<CustomerResponse> getCustomerList(CustomerSreachRequest request) {

        int page = request.page();
        int size = request.size();
        String keyword = request.keyword();

        PageRequest pageable = PageRequest.of(
            page, 
            size,
            Sort.by("deletedAt").descending()
        );

        Page<Customer> customerPage = customerService.searchCustomer(keyword, pageable);

        List<CustomerResponse> data = customerPage.getContent()
                                        .stream()
                                        .map(c -> customerMapper.toCustomerResponse(c))
                                        .toList();

        return new PageResponse<>(
            data,
            customerPage.getNumber(),
            customerPage.getSize(),
            customerPage.getTotalElements(),
            customerPage.getTotalPages()
        );
    }

    @Override
    public CustomerResponse getCustomer(Long customerId) {

        return customerMapper.toCustomerResponse(customerService.getCustomerById(customerId));
    }

    @Override
    public byte[] excelTableExport(CustomerSreachRequest request) {
        
        String keyword = request.keyword();

        List<Customer> data = customerService.getAllForExport(keyword);

        String[] headers = {"Tên", "SĐT", "Email", "Địa chiề", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"};

        List<Function<Customer, Object>> fieldMappers = List.of(
            customer -> customer.getName(),
            customer -> customer.getPhone(),
            customer -> customer.getEmail(),
            customer -> customer.getAddress(),
            customer -> customer.getCreatedAt(),
            customer -> customer.getUpdatedAt(),
            customer -> customer.getDeletedAt()
        );

        return excelExportService.exportTableExcel(
            data,
            "customer",
            "Danh sách khách hàng",
            headers,
            fieldMappers
        );
    }

    
}
