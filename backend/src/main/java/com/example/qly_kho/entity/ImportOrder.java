package com.example.qly_kho.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "import_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImportOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "supplier_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_import_order_supplier")
    )
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(    
        name = "created_by", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_import_order_user")
    )
    private User createdBy;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "DRAFT";

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
        mappedBy = "importOrder", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    @BatchSize(size = 20)
    private Set<ImportOrderDetails> importDetails = new HashSet<>();
    
    public static ImportOrder createImportOrder(Supplier supplier, User createdBy, String note) {
        ImportOrder order = new ImportOrder();

        order.supplier = supplier;
        order.createdBy = createdBy;
        order.note = note;

        return order;
    }
}
