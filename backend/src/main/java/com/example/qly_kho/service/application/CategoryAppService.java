package com.example.qly_kho.service.application;

import java.util.List;

import com.example.qly_kho.dto.request.category.CategoryCreateRequest;
import com.example.qly_kho.dto.request.category.CategorySearchRequest;
import com.example.qly_kho.dto.request.category.CategoryUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.category.CategoryResponse;

public interface CategoryAppService {
    //tạo category
    CategoryResponse createCategory(CategoryCreateRequest request, String ip, Long actorUserId);

    //cap nhat category
    CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest request, String ip, Long actorUserId);

    void deleteCategory(Long categoryId, String ip, Long actorUserId);

    List<CategoryResponse> getCategoryListAndDeletedIsNull();

    //lấy danh sách theo keyword
    PageResponse<CategoryResponse> getCategoryList(CategorySearchRequest request);

    CategoryResponse getCategory(Long categoryId);

    //xuất file excel theo keyword
    byte[] excelTableExport(CategorySearchRequest request);
}
