package com.example.qly_kho.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "products",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_product_sku", columnNames = "sku")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku", nullable = false, length = 50)
    private String sku;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "category_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_product_category")
    )
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "unit_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_product_unit")
    )
    private Unit unit;

    @Column(name = "selling_price", precision = 15, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<ProductImage> productImages = new HashSet<>();

    @OneToMany(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<ProductSupplier> productSuppliers = new HashSet<>();

    @OneToOne(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = false,
        fetch = FetchType.LAZY
    )
    private Inventory inventory;

    @OneToOne(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = false,
        fetch = FetchType.LAZY
    )   
    private DefectiveInventory defectiveInventory;

    @OneToMany(
        mappedBy = "product",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<ImportOrderDetails> importOrderDetails = new HashSet<>();

    @OneToMany(
        mappedBy = "product",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<ProductBatch> productBatches = new HashSet<>();
    
    public static Product createProduct(String sku, String name, Category category, Unit unit, String description, BigDecimal sellingPrice) {
        Product product = new Product();

        product.sku = sku;
        product.name = name;
        product.category = category;
        product.unit = unit;
        product.description = description;
        product.sellingPrice = sellingPrice;

        return product;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changeUnit(Unit unit) {
        this.unit = unit;
    }

    public void changeSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void disableProduct() {
        this.active = false;
    }
    
    public void enableProduct() {
        this.active = true;
    }

    public void deleteProduct() {
        this.deletedAt = LocalDateTime.now();
    }
}