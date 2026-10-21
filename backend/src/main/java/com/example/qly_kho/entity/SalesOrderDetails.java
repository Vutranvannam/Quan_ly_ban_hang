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

// CREATE TABLE sales_order_details (
//     id BIGSERIAL PRIMARY KEY,

//     sales_order_id BIGINT NOT NULL,
//     product_batch_id BIGINT NOT NULL,

//     quantity DECIMAL(15,3) NOT NULL CHECK (quantity > 0),

//     unit_price NUMERIC(15,2) CHECK (unit_price >= 0),

//     note TEXT,

//     CONSTRAINT fk_sales_detail_order
//         FOREIGN KEY (sales_order_id)
//         REFERENCES sales_orders(id) ON DELETE CASCADE,

//     CONSTRAINT fk_sales_detail_batch
//         FOREIGN KEY (product_batch_id)
//         REFERENCES product_batches(id)
// );

@Entity
@Table(name = "sales_order_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesOrderDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "sales_order_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_sales_detail_order")
    )
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_batch_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_sales_detail_batch")
    )
    private ProductBatch productBatch;
    
    @Column(name = "quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(name = "sale_price", precision = 15, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    public static SalesOrderDetails createSalesOrderDetail(SalesOrder saleOrder, ProductBatch productBatch, BigDecimal quantity, BigDecimal salePrice, String note) {
        SalesOrderDetails detail = new SalesOrderDetails();

        detail.salesOrder = saleOrder;
        detail.productBatch = productBatch;
        detail.quantity = quantity;
        detail.salePrice = salePrice;
        detail.note = note;

        return detail;
    }
}