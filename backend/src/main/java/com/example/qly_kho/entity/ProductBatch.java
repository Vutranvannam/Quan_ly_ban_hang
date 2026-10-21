package com.example.qly_kho.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_batches")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductBatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "import_order_detail_id", 
        nullable = false, 
        foreignKey =@ForeignKey(name = "fk_batch_import_detail")
    )
    private ImportOrderDetails importOrderDetail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_batch_product")
    )
    private Product product;

    @Column(name = "batch_code", length = 100, nullable = false)
    private String batchCode;

    @Column(name = "import_price", precision = 15, scale = 2)
    private BigDecimal importPrice;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(name = "remaining_quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal remainingQuantity;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "GOOD";

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @OneToMany(
        mappedBy = "productBatch", 
        cascade = CascadeType.ALL, 
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<InventoryLog> inventoryLogs = new HashSet<>();

    @OneToMany(
        mappedBy = "productBatch", 
        cascade = CascadeType.ALL, 
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<DefectiveInventoryLog> defectiveInventoryLogs = new HashSet<>();

    @OneToMany(
        mappedBy = "productBatch", 
        cascade = CascadeType.ALL, 
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<SupplierReturnDetail> supplierReturnDetails = new HashSet<>();

    @OneToMany(
        mappedBy = "productBatch", 
        cascade = CascadeType.ALL, 
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<CustomerReturnDetails> customerReturnDetails = new HashSet<>();
    @OneToMany(
        mappedBy = "productBatch", 
        cascade = CascadeType.ALL, 
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<SalesOrderDetails> salesOrderDetails = new HashSet<>();
    
    @OneToMany(
        mappedBy = "productBatch", 
        cascade = CascadeType.ALL, 
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<InventoryAdjustmentDetails> inventoryAdjustmentDetails = new HashSet<>();

    public static ProductBatch createProductBatch(ImportOrderDetails importOrderDetail, Product product, String batchCode, BigDecimal importPrice, BigDecimal quantity, LocalDate manufactureDate, LocalDate expiryDate) {
        ProductBatch batche = new ProductBatch();

        batche.importOrderDetail = importOrderDetail;
        batche.product = product;
        batche.batchCode = batchCode;
        batche.importPrice = importPrice;
        batche.quantity = quantity;
        batche.remainingQuantity = quantity; // Ban đầu số lượng còn lại bằng số lượng nhập
        batche.manufactureDate = manufactureDate;
        batche.expiryDate = expiryDate;

        return batche;
    }
}
