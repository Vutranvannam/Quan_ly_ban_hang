package com.example.qly_kho.entity;

import java.time.LocalDate;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
        name = "customer_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_sales_customer")
    )
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
        name = "created_by",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_sales_user")
    )
    private User createdBy;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

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
        mappedBy = "salesOrder", 
        orphanRemoval = true
    )
    @BatchSize(size = 20)
    private Set<SalesOrderDetails> salesOrderDetails = new HashSet<>();

    @OneToOne(
        mappedBy = "salesOrder",
        fetch = FetchType.LAZY,
        optional = false
    )
    private CustomerReturn customerReturn;
    
    public static SalesOrder createSaleOrder(Customer customer, User createdBy, String status, LocalDate manufactureDate, LocalDate expiryDate, String note) {
        SalesOrder order = new SalesOrder();

        order.customer = customer;
        order.createdBy = createdBy;
        order.status = status;
        order.manufactureDate = manufactureDate;
        order.expiryDate = expiryDate;
        order.note = note;

        return order;
    }
}
