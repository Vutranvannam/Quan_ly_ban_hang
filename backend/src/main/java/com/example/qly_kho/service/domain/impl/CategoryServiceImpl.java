package com.example.qly_kho.service.domain.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.Category;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.repository.CategoryRepository;
import com.example.qly_kho.service.domain.CategoryService;
import com.example.qly_kho.specification.CategorySpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;

    @Override
    public void existsByName(String name) {

        if (categoryRepository.existsByName(name)) {
            throw new DuplicateException(
                String.format("Category with name: %s already exists", name)
            );
        }
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> 
                new NotFoundException("Category not found")
            );
    }

    @Override
    public List<Category> findByDeletedIsNull() {
        
        return categoryRepository.findByDeletedAtIsNull();
    }

    @Override
    public Category saveCategory(Category category) {

        try{
            return categoryRepository.save(category);
        
        } catch (DataIntegrityViolationException ex) {

            throw new DuplicateException("Duplicate data");
        }

    }

    @Override
    public void deleteCategory(Category category) {

        category.deleteCategory();

        categoryRepository.save(category);
    }

    @Override
    public Page<Category> searchCategory(String keyword, Pageable pageable) {
        Specification<Category> specification = Specification
                                                    .where(CategorySpecification.hasName(keyword))
                                                    .or(CategorySpecification.hasDescription(keyword));
                            
        return categoryRepository.findAll(specification, pageable);
    }

    @Override
    public List<Category> getAllForExport(String keyword) {
        Specification<Category> specification = Specification
                                                    .where(CategorySpecification.hasName(keyword))
                                                    .or(CategorySpecification.hasDescription(keyword));
                            
        return categoryRepository.findAll(specification);
    }  
    
}
