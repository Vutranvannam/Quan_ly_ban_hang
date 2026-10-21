package com.example.qly_kho.entity;

import java.math.BigDecimal;
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
@Table(name = "import_order_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImportOrderDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "import_order_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_import_detail_order")
    )
    private ImportOrder importOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_import_detail_product")
    )
    private Product product;

    @Column(name = "import_price", nullable = false, precision = 15, scale = 3  )
    private BigDecimal importPrice;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @OneToMany(
        mappedBy = "importOrderDetail", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    @BatchSize(size = 20)
    private Set<ProductBatch> productBatches = new HashSet<>();


    public static ImportOrderDetails createImportOrderDetail(ImportOrder importOrder, Product product, BigDecimal quantity, String note) {
        ImportOrderDetails detail = new ImportOrderDetails();

        detail.importOrder = importOrder;
        detail.product = product;
        detail.quantity = quantity;
        detail.note = note;

        return detail;
    }
}
