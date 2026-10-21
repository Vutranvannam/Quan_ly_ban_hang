package com.example.qly_kho.service.application.impl;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.constant.ActionConstants;
import com.example.qly_kho.dto.request.category.CategoryCreateRequest;
import com.example.qly_kho.dto.request.category.CategorySearchRequest;
import com.example.qly_kho.dto.request.category.CategoryUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.category.CategoryResponse;
import com.example.qly_kho.entity.Category;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.mapper.CategoryMapper;
import com.example.qly_kho.service.application.CategoryAppService;
import com.example.qly_kho.service.application.ExcelExportService;
import com.example.qly_kho.service.domain.ActivityLogService;
import com.example.qly_kho.service.domain.CategoryService;
import com.example.qly_kho.service.domain.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryAppServiceImpl implements CategoryAppService{

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ActivityLogService activityLogService;
    private final ExcelExportService excelExportService;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request, String ip, Long actorUserId) {
        
        categoryService.existsByName(request.name());
        
        Category category = Category.createCategory(request.name(), request.description());

        Category savedCategory = categoryService.saveCategory(category);

        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.CREATE, 
            ActionConstants.ENTITY_CATEGORY, 
            savedCategory.getId(), 
            "Category created successfully", 
            ip
        );
        
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest request, String ip, Long actorUserId) {
        
        Category category = categoryService.findById(categoryId);

        if (category.getDeletedAt() != null) {
            throw new NotFoundException(
                String.format("Category with id %d has already been deleted", categoryId)
            );
        }

        if(request.description() != null) {
            category.changeDescription(request.description());
        }

        Category savedCategory = categoryService.saveCategory(category);

        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.UPDATE, 
            ActionConstants.ENTITY_CATEGORY, 
            savedCategory.getId(), 
            "Category updated successfully", 
            ip
        );

        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId, String ip, Long actorUserId) {

        Category category = categoryService.findById(categoryId);
        
        if (category.getDeletedAt() != null) {
            throw new DuplicateException(
                String.format("Category with id %d has already been deleted", categoryId)
            );
        }

        productService.existsByCategoryIdAndDeletedAtIsNull(categoryId);

        categoryService.deleteCategory(category);

        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.DELETE, 
            ActionConstants.ENTITY_CATEGORY, 
            categoryId, 
            "Category deleted successfully", 
            ip
        );
    }

    @Override
    public List<CategoryResponse> getCategoryListAndDeletedIsNull() {
        List<Category> categories = categoryService.findByDeletedIsNull();

        return categories.stream()
                        .map(c -> categoryMapper.toCategoryResponse(c))
                        .toList();
    }

    

    @Override
    public CategoryResponse getCategory(Long categoryId) {
       
        return categoryMapper.toCategoryResponse(categoryService.findById(categoryId));
    }

    @Override
    public PageResponse<CategoryResponse> getCategoryList(CategorySearchRequest request) {
        int page = request.page();
        int size = request.size();
        String keyword = request.keyword();

        PageRequest pageable = PageRequest.of(
            page, 
            size,
            Sort.by("deletedAt").descending()
        );

        Page<Category> categoryPage = categoryService.searchCategory(keyword, pageable);

        List<CategoryResponse> data = categoryPage.getContent()
                                        .stream()
                                        .map(c -> categoryMapper.toCategoryResponse(c))
                                        .toList();

        return new PageResponse<> (
            data,
            categoryPage.getNumber(),
            categoryPage.getSize(),
            categoryPage.getTotalElements(),
            categoryPage.getTotalPages()
        );
    }

    @Override
    public byte[] excelTableExport(CategorySearchRequest request) {
        
        String keyword = request.keyword();

        List<Category> data = categoryService.getAllForExport(keyword);

        String[] headers = {"Tên", "Mô tả", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"};

        List<Function<Category, Object>> fieldMappers = List.of(
            category -> category.getName(),
            category -> category.getDescription(),
            category -> category.getCreatedAt(),
            category -> category.getUpdatedAt(),
            category -> category.getDeletedAt()
        );

        return excelExportService.exportTableExcel(
            data,
            "category",
            "Danh sách danh mục",
            headers,
            fieldMappers
        );
    }
}
