package com.example.qly_kho.controller;

import java.util.List;

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

import com.example.qly_kho.dto.request.category.CategoryCreateRequest;
import com.example.qly_kho.dto.request.category.CategorySearchRequest;
import com.example.qly_kho.dto.request.category.CategoryUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.category.CategoryResponse;
import com.example.qly_kho.security.cache.UserCache;
import com.example.qly_kho.service.application.CategoryAppService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryAppService categoryApplicationService;

    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    @PostMapping("/add")
    public ResponseEntity<Void> createCategory(
            @RequestBody @Valid CategoryCreateRequest request, 
            HttpServletRequest httpServletRequest,
            @AuthenticationPrincipal UserCache user
    ) {

        categoryApplicationService.createCategory(
            request, 
            httpServletRequest.getRemoteAddr(), 
            user.getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long categoryId, 
            @Valid @RequestBody CategoryUpdateRequest request, 
            HttpServletRequest httpServletRequest,
            @AuthenticationPrincipal UserCache user
    ) {

        return ResponseEntity.ok(
            categoryApplicationService.updateCategory(
                categoryId, 
                request, 
                httpServletRequest.getRemoteAddr(), 
                user.getId()
            )
        );
        
    }
    
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long categoryId, 
            HttpServletRequest httpServletRequest,
            @AuthenticationPrincipal UserCache user
    ) {
        categoryApplicationService.deleteCategory(
            categoryId, 
            httpServletRequest.getRemoteAddr(), 
            user.getId()
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    @GetMapping("/list")
    public ResponseEntity<PageResponse<CategoryResponse>> getCategoryList(@Valid @ModelAttribute CategorySearchRequest request) {

        return ResponseEntity.ok(categoryApplicationService.getCategoryList(request));
    }

    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {

        return ResponseEntity.ok(categoryApplicationService.getCategory(categoryId));
    }

    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    @GetMapping("/list/export-excel")
    public ResponseEntity<byte[]> excelTableExport(@Valid @ModelAttribute CategorySearchRequest request) {
        byte[] file = categoryApplicationService.excelTableExport(request);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=categories.xlsx")
                .body(file);
    }

    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    @GetMapping("/list-all")
    public ResponseEntity<List<CategoryResponse>> getCategoryListAndDeletedIsNull() {
        return ResponseEntity.ok(categoryApplicationService.getCategoryListAndDeletedIsNull());
    }
}
