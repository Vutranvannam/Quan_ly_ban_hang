package com.example.qly_kho.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "inventory_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "product_batch_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_inventory_log_product_batch")
    )
    private ProductBatch productBatch;

    @Column(name = "change_amount", nullable = false, precision = 15, scale = 3)
    private BigDecimal changeAmount;

    @Column(name = "type", nullable = false)
    private String type; // IMPORT, SALE, RETURN, ADJUST

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static InventoryLog createInventoryLog(ProductBatch productBatch, String type, String note, BigDecimal changeAmount, Long createdBy) {
        InventoryLog newLog = new InventoryLog();

        newLog.productBatch = productBatch;
        newLog.type = type;
        newLog.note = note;
        newLog.changeAmount = changeAmount;
        newLog.createdBy = createdBy;

        return newLog;
    }

}
