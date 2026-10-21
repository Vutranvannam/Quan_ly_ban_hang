package com.example.qly_kho.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name  = "uk_users_username", columnNames = "username"),
        @UniqueConstraint(name = "uk_users_email", columnNames = "email")
    }
)
@Getter
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "full_name", length = 255)
    private String fullName;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(name = "auth_version", nullable = false)
    private Long authVersion = 1L;

    @Column(name = "permission_version", nullable = false)
    private Long permissionVersion = 1L;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<UserProvider> userProviders = new HashSet<>();

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<RefreshToken> refreshTokens = new HashSet<>();

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<ActivityLog> activityLogs = new HashSet<>();

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<InventoryAdjustment> inventoryAdjustments = new HashSet<>();

    @OneToMany(
        mappedBy = "createdBy",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<ImportOrder> importOrders = new HashSet<>();

    @OneToMany(
        mappedBy = "createdBy",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<SupplierReturn> supplierReturns = new HashSet<>();

    @OneToMany(
        mappedBy = "createdBy",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<SalesOrder> salesOrders = new HashSet<>();

    @OneToMany(
        mappedBy = "createdBy",
        orphanRemoval = false
    )
    @BatchSize(size = 20)
    private Set<CustomerReturn> customerReturns = new HashSet<>();

    public static User createUser(String username, String passwordHash, String email, String fullName) {
        User newUser = new User();

        newUser.username = username;
        newUser.passwordHash = passwordHash;
        newUser.email = email;
        newUser.fullName = fullName;

        return newUser;
    }

    public void changeFullName(String fullName) {
        this.fullName = fullName;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void addRole(Role role) {
        if (role == null) return;

        this.roles.add(role);
        role.getUsers().add(this);
    }
    public void removeRole(Role role) {
        if (role == null) return;
        
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void increaseAuthVersion() {
        this.authVersion++;
    }

    public void increasePermissionVersion() {
        this.permissionVersion++;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void disable() {
        this.enabled = false;
    }

    public void enable() {
        this.enabled = true;
    }

    public void lock() {
        this.accountNonLocked = false;
    }

    public void unlock() {
        this.accountNonLocked = true;
    }

    public void deletedUser() {
        this.deletedAt = LocalDateTime.now();
        this.enabled = false;
    }
}
