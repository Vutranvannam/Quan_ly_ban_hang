package com.example.qly_kho.service.application;

import com.example.qly_kho.dto.request.customer.CustomerCreateRequest;
import com.example.qly_kho.dto.request.customer.CustomerSreachRequest;
import com.example.qly_kho.dto.request.customer.CustomerUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.customer.CustomerResponse;

public interface CustomerAppService {

    CustomerResponse createCustomer(CustomerCreateRequest request, String ip, Long actorUserId);

    CustomerResponse updateCustomer(Long customerId, CustomerUpdateRequest request, String ip, Long actorUserId);

    void deleteCustomer(Long customerId, String ip, Long actorUserId);

    PageResponse<CustomerResponse> getCustomerList(CustomerSreachRequest request);

    CustomerResponse getCustomer(Long customerId);

    byte[] excelTableExport(CustomerSreachRequest request);
}
