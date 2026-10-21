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

import com.example.qly_kho.dto.request.customer.CustomerCreateRequest;
import com.example.qly_kho.dto.request.customer.CustomerSreachRequest;
import com.example.qly_kho.dto.request.customer.CustomerUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.customer.CustomerResponse;
import com.example.qly_kho.security.cache.UserCache;
import com.example.qly_kho.service.application.CustomerAppService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerAppService customerApplicationService;

    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    @PostMapping("/add")
    public ResponseEntity<Void> createCustomer(
        @RequestBody @Valid CustomerCreateRequest request, 
        HttpServletRequest httpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {

        customerApplicationService.createCustomer(
            request,
            httpServletRequest.getRemoteAddr(),
            user.getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long customerId, 
            @Valid @RequestBody CustomerUpdateRequest request, 
            HttpServletRequest httpServletRequest,
            @AuthenticationPrincipal UserCache user
    ) {

        return ResponseEntity.ok(
            customerApplicationService.updateCustomer(
                customerId, 
                request, 
                httpServletRequest.getRemoteAddr(), 
                user.getId()
            )
        );
    }
    
    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
        @PathVariable Long customerId, 
        HttpServletRequest httpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {
        customerApplicationService.deleteCustomer(
            customerId, 
            httpServletRequest.getRemoteAddr(),
            user.getId()
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    @GetMapping("/list")
    public PageResponse<CustomerResponse> getCustomerList(@Valid @ModelAttribute CustomerSreachRequest request) {

        return customerApplicationService.getCustomerList(request);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long customerId) {

        return ResponseEntity.ok(customerApplicationService.getCustomer(customerId));
    }

    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    @GetMapping("/list/export-excel")
    public ResponseEntity<byte[]> excelTableExport(@Valid @ModelAttribute CustomerSreachRequest request) {
        byte[] file = customerApplicationService.excelTableExport(request);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=categories.xlsx")
                .body(file);
    }
}
