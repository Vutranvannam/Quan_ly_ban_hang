package com.example.qly_kho.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplier_returns")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupplierReturn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "supplier_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_return_supplier")
    )
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "created_by",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_return_user")
    )
    private User createdBy;

    @Column(name = "status", nullable = false, length = 30)
    private String status;
    //         DRAFT       : đang tạo
    //         PENDING     : chờ duyệt
    //         APPROVED    : đã duyệt
    //         COMPLETED   : đã trả hàng
    //         CANCELLED   : hủy

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "approved_at", updatable = false)
    private LocalDateTime approvedAt;

    @Column(name = "completed_at", updatable = false)
    private LocalDateTime completedAt;

    @Column(name = "cancelled_at", updatable = false)
    private LocalDateTime cancelledAt;

    @OneToMany(
        mappedBy = "supplierReturn", 
        orphanRemoval = true
    )
    @BatchSize(size = 20)
    private Set<SupplierReturnDetail> returnDetails = new HashSet<>();

    public static SupplierReturn createSupplierReturn(Supplier supplier, User createdBy, String note) {
        SupplierReturn newReturn = new SupplierReturn();

        newReturn.supplier = supplier;
        newReturn.createdBy = createdBy;
        newReturn.note = note;
        newReturn.status = "DRAFT";

        return newReturn;
    }
}
