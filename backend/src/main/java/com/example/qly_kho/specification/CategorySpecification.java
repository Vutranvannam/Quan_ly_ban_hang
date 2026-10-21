package com.example.qly_kho.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.qly_kho.entity.Category;

public class CategorySpecification {
    public static Specification<Category> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;

            return cb.like(cb.lower(root.get("name")), "%"+name.toLowerCase()+"%");
        };
    }

    public static Specification<Category> hasDescription(String description) {
        return (root, query, cb) -> {
            if (description == null || description.isBlank()) return null;

            return cb.like(cb.lower(root.get("description")), "%"+description.toLowerCase()+"%");
        };
    }
}
