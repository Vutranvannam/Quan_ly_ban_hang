package com.example.qly_kho.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.qly_kho.entity.Customer;

public class CustomerSpecification {
    public static Specification<Customer> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;

            return cb.like(cb.lower(root.get("name")), "%"+name.toLowerCase()+"%");
        };
    }

    public static Specification<Customer> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) return null;

            return cb.like(cb.lower(root.get("email")), "%"+email.toLowerCase()+"%");
        };
    }

    public static Specification<Customer> hasPhone(String phone) {
        return (root, query, cb) -> {
            if (phone == null || phone.isBlank()) return null;

            return cb.like(cb.lower(root.get("phone")), "%"+phone.toLowerCase()+"%");
        };
    }

    public static Specification<Customer> hasAddress(String address) {
        return (root, query, cb) -> {
            if (address == null || address.isBlank()) return null;

            return cb.like(cb.lower(root.get("address")), "%"+address.toLowerCase()+"%");
        };
    }
}
