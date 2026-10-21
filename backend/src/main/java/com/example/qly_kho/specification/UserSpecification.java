package com.example.qly_kho.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.qly_kho.entity.User;

public class UserSpecification {
    
    public static Specification<User> hasUsername(String username) {
        return (root, query, cb) -> {
            if (username == null || username.isBlank()) return null;

            return cb.like(cb.lower(root.get("username")), "%"+username.toLowerCase()+"%");
        };
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) return null;

            return cb.like(cb.lower(root.get("email")), "%"+email.toLowerCase()+"%");
        };
    }

    public static Specification<User> hasFullName(String fullName) {
        return (root, query, cb) -> {
            if (fullName == null || fullName.isBlank()) return null;

            return cb.like(cb.lower(root.get("fullName")), "%"+fullName.toLowerCase()+"%");
        };
    }
}
