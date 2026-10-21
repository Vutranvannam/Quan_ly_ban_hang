package com.example.qly_kho.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qly_kho.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String token);
    
    // Lấy tất cả token chưa bị revoked của user, trả về Set
    Set<RefreshToken> findAllByUserIdAndRevokedFalse(Long userId);
    
    // Lấy tất cả token của user (bao gồm revoked), trả về Set
    Set<RefreshToken> findAllByUserId(Long userId);
}
