package com.example.qly_kho.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
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
@Table (
    name = "suppliers",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_suppliers_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_suppliers_phone", columnNames = "phone"),
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column ( name = "name", nullable = false, length = 255)
    private String name;

    @Column ( name = "phone", nullable = false, length = 50 ) 
    private String phone;

    @Column ( name = "email", nullable = false, length = 255)
    private String email;

    @Column ( name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;
     
    @Column ( name = "created_at", updatable = false )
    @CreationTimestamp 
    private LocalDateTime createdAt;

    @Column ( name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column ( name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(
        mappedBy = "supplier",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<ProductSupplier> productSuppliers = new HashSet<>();

    @OneToMany(
        mappedBy = "supplier",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<ImportOrder> importOrders = new HashSet<>();

    @OneToMany(
        mappedBy = "supplier",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<SupplierReturn> supplierReturns = new HashSet<>();

    public static Supplier createSupplier(String name, String phone, String email, String address) {
        Supplier newSupplier = new Supplier();

        newSupplier.name = name;
        newSupplier.phone = phone;
        newSupplier.email = email;
        newSupplier.address = address;

        return newSupplier;
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

    public void deleteSupplier() {
        this.deletedAt = LocalDateTime.now();
    }
}
