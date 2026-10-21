package com.example.qly_kho.service.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.qly_kho.entity.Category;

public interface CategoryService {

    //check trùng tên category
    void existsByName(String name);

    Category findById(Long id);

    List<Category> findByDeletedIsNull();
    
    //save category
    Category saveCategory(Category category);

    void deleteCategory(Category category);

    Page<Category> searchCategory(String keyword, Pageable pageable);

    List<Category> getAllForExport(String keyword);
}
