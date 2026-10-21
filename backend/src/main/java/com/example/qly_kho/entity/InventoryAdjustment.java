package com.example.qly_kho.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "inventory_adjustments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryAdjustment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "created_by",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_inventory_adjustment_user")
    )
    private User user;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @OneToMany(
        mappedBy = "inventoryAdjustment",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<InventoryAdjustmentDetails> adjustmentDetails = new HashSet<>();

    public static InventoryAdjustment createInventoryAdjustment(User user, String note) {
        InventoryAdjustment adjustment = new InventoryAdjustment();
        
        adjustment.user = user;
        adjustment.note = note;

        return adjustment;
    }
}
