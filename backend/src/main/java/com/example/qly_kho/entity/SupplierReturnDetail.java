package com.example.qly_kho.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// CREATE TABLE supplier_return_details (
//     id BIGSERIAL PRIMARY KEY,

//     supplier_return_id BIGINT NOT NULL,
//     product_batch_id BIGINT NOT NULL,

//     quantity DECIMAL(15, 3) NOT NULL CHECK (quantity > 0),

//     reason TEXT,

//     CONSTRAINT fk_return_detail_return FOREIGN KEY (supplier_return_id) REFERENCES supplier_returns(id) ON DELETE CASCADE,
//     CONSTRAINT fk_return_detail_batch FOREIGN KEY (product_batch_id) REFERENCES product_batches(id)
// );
@Entity
@Table(name = "supplier_return_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupplierReturnDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "supplier_return_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_return_detail_return")
    )
    private SupplierReturn supplierReturn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_batch_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_return_detail_batch")
    )
    private ProductBatch productBatch;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    public static SupplierReturnDetail createSupplierReturnDetail(SupplierReturn supplierReturn, ProductBatch productBatch, BigDecimal quantity, String reason) {
        SupplierReturnDetail detail = new SupplierReturnDetail();

        detail.supplierReturn = supplierReturn;
        detail.productBatch = productBatch;
        detail.quantity = quantity;
        detail.reason = reason;
        
        return detail;
    }
}
