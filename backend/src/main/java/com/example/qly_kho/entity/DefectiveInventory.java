package com.example.qly_kho.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// CREATE TABLE defective_inventory (
//     id BIGSERIAL PRIMARY KEY,

//     product_id BIGINT NOT NULL,
    
//     quantity NUMERIC(15,3) NOT NULL CHECK (quantity >= 0),

//     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    // deleted_at TIMESTAMP,

//     CONSTRAINT fk_defective_product 
//         FOREIGN KEY (product_id) REFERENCES products(id),
// );
@Entity
@Table(name = "defective_inventory")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefectiveInventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_defective_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static DefectiveInventory createDefectiveInventory(Product product, BigDecimal quantity) {
        DefectiveInventory newDefectiveInventory = new DefectiveInventory();

        newDefectiveInventory.product = product;
        newDefectiveInventory.quantity = quantity;

        return newDefectiveInventory;
    }
}
