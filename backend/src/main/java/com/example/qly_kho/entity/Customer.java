package com.example.qly_kho.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "customers",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_customers_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_customers_phone", columnNames = "phone"),
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer { 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address", columnDefinition = "TEXT", nullable = false)
    private String address;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(
        mappedBy = "customer",
         orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<SalesOrder> salesOrders = new HashSet<>();
    
    public static Customer createCustomer(String name, String phone, String email, String address) {
        Customer newCustomer = new Customer();

        newCustomer.name = name;
        newCustomer.phone = phone;
        newCustomer.email = email;
        newCustomer.address = address;

        return newCustomer;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void deleteCustomer() {
        this.deletedAt = LocalDateTime.now();
    }
}
