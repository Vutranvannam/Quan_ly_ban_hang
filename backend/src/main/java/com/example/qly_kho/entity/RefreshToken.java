package com.example.qly_kho.entity;

import java.time.LocalDateTime;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "refresh_tokens",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_refresh_token_token_hash", columnNames = "token_hash")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_hash", nullable = false, length = 500)
    private String tokenHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_refresh_token_user")
    )
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "revoked")
    private boolean revoked = false;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    public static RefreshToken createRefreshToken(String tokenHash, User user, LocalDateTime expiryDate) {
        RefreshToken newRefreshToken = new RefreshToken();

        newRefreshToken.tokenHash = tokenHash;
        newRefreshToken.user = user;
        newRefreshToken.expiryDate = expiryDate;

        return newRefreshToken;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
