package com.example.qly_kho.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_returns")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerReturn {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "sales_order_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_return_sales_order")
    )
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "created_by", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_return_user")
    )
    private User createdBy;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

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
        mappedBy = "customerReturn",
        orphanRemoval = false
    )
    private Set<CustomerReturnDetails> returnDetails = new HashSet<>();

    public static CustomerReturn createCustomerReturn(SalesOrder salesOrder, User createdBy, String status, String note) {
        CustomerReturn customerReturn = new CustomerReturn();

        customerReturn.salesOrder = salesOrder;
        customerReturn.createdBy = createdBy;
        customerReturn.status = status;
        customerReturn.note = note;

        return customerReturn;
    }
}
