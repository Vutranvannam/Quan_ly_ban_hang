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

@Entity
@Table(name = "inventory_adjustment_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryAdjustmentDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "adjustment_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_inventory_adjustment_details_adjustment")
    )
    private InventoryAdjustment inventoryAdjustment;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_batch_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_inventory_adjustment_details_product_batch")
    )
    private ProductBatch productBatch;

    @Column(name = "system_quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal systemQuantity;

    @Column(name = "actual_quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal actualQuantity;

    @Column(name = "difference", nullable = false, precision = 15, scale = 3)
    private BigDecimal difference;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    public static InventoryAdjustmentDetails createInventoryAdjustmentDetails(InventoryAdjustment inventoryAdjustment, ProductBatch productBatch, BigDecimal systemQuantity, BigDecimal actualQuantity, String reason) {
        InventoryAdjustmentDetails details = new InventoryAdjustmentDetails();
        
        details.inventoryAdjustment = inventoryAdjustment;
        details.productBatch = productBatch;
        details.systemQuantity = systemQuantity;
        details.actualQuantity = actualQuantity;
        details.difference = actualQuantity.subtract(systemQuantity);
        details.reason = reason;
        
        return details;
    }
}
