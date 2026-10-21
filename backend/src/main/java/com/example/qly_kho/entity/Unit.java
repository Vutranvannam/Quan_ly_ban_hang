package com.example.qly_kho.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "units",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_units_name", columnNames = "name")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Unit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(
        mappedBy = "unit",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<Product> products = new HashSet<>();

    public static Unit createUnit(String name) {
        Unit newUnit = new Unit();

        newUnit.name = name;
        
        return newUnit;
    }

}
