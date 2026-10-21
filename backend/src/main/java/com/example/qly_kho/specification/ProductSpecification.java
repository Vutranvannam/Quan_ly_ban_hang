package com.example.qly_kho.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.qly_kho.entity.Product;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;

            return cb.like(cb.lower(root.get("name")), "%"+name.toLowerCase()+"%");
        };
    }

    public static Specification<Product> hasSku(String sku) {
        return (root, query, cb) -> {
            if (sku == null || sku.isBlank()) return null;

            return cb.like(cb.lower(root.get("sku")), "%"+sku.toLowerCase()+"%");
        };
    }

    public static Specification<Product> hasNameCatgory(String nameCatgory) {
        return (root, query, cb) -> {
            if (nameCatgory == null || nameCatgory.isBlank()) return null;
            Join<Object, Object> category = root.join("category", JoinType.LEFT);
            return cb.like(
                cb.lower(category.get("name")), 
                "%"+nameCatgory.toLowerCase()+"%"
            );
        };
    }

    public static Specification<Product> hasUnitName(String unitName) {
    return (root, query, cb) -> {
        if (unitName == null || unitName.isBlank()) return null;
        Join<Object, Object> unit = root.join("unit", JoinType.LEFT);
        return cb.like(
            cb.lower(unit.get("name")),
            "%" + unitName.toLowerCase() + "%"
        );
    };
}
}
