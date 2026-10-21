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
@Table(name = "customer_return_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerReturnDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "customer_return_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_customer_return_detail_return")
    )
    private CustomerReturn customerReturn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_batch_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_customer_return_detail_batch")
    )
    private ProductBatch productBatch;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    public static CustomerReturnDetails createCustomerReturnDetails(CustomerReturn customerReturn, ProductBatch productBatch, BigDecimal quantity, String reason) {
        CustomerReturnDetails details = new CustomerReturnDetails();

        details.customerReturn = customerReturn;
        details.productBatch = productBatch;
        details.quantity = quantity;
        details.reason = reason;

        return details;
    }
}
