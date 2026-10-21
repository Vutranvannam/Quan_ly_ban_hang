package com.example.qly_kho.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "defective_inventory_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefectiveInventoryLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_batch_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_defective_log_product_batch")
    )
    private ProductBatch productBatch;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "change_amount", precision = 15, scale = 3, nullable = false)
    private BigDecimal changeAmount;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static DefectiveInventoryLog createDefectiveInventoryLog(ProductBatch productBatch, String type, String note, BigDecimal changeAmount, Long createdBy) {
        DefectiveInventoryLog newLog = new DefectiveInventoryLog();

        newLog.productBatch = productBatch;
        newLog.type = type;
        newLog.note = note;
        newLog.changeAmount = changeAmount;
        newLog.createdBy = createdBy;

        return newLog;
    }
}
