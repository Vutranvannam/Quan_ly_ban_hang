package com.example.qly_kho.service.domain.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.RefreshToken;
import com.example.qly_kho.entity.User;
import com.example.qly_kho.exception.ErrorCode;
import com.example.qly_kho.exception.custom.AppException;
import com.example.qly_kho.exception.custom.UnauthorizedException;
import com.example.qly_kho.repository.RefreshTokenRepository;
import com.example.qly_kho.security.jwt.JwtProvider;
import com.example.qly_kho.service.domain.RefreshTokenService;
import com.example.qly_kho.service.domain.UserService;
import com.example.qly_kho.util.SHA256Hasher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    
    @Override
    public void addRefreshTokenFromUsername(String token, String username) {

        String tokenHash = SHA256Hasher.hash(token);
        User user = userService.findByUsername(username);
        LocalDateTime expiryDate = LocalDateTime.ofInstant(Instant.now().plusMillis(jwtProvider.getRefreshExpiration()), ZoneId.systemDefault());
        
        try {
            refreshTokenRepository.save(RefreshToken.createRefreshToken(tokenHash, user, expiryDate));
        } catch (Exception e) {
            throw new AppException(
                ErrorCode.INTERNAL_ERROR, 
                String.format("Failed to save refresh token in RefreshTokenService: %s", e.getMessage())
            );
        }
    }

    @Override
    public void validateRefreshToken(String token) {
        String tokenHash = SHA256Hasher.hash(token);
        LocalDateTime now = LocalDateTime.now();

        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenHash(tokenHash)
                .orElseThrow(() -> new UnauthorizedException(
                        "Refresh token is invalid"
                ));

        if (refreshToken.isRevoked()) {
            throw new UnauthorizedException("Refresh token is revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(now)) {
            throw new UnauthorizedException("Refresh token is expired");
        }
    }

    @Override
    public void revokeRefreshTokenFromUserId(Long userId) {


        Set<RefreshToken> tokens = refreshTokenRepository.findAllByUserId(userId);
        tokens.forEach(token -> token.setRevoked(true));
        try {
            refreshTokenRepository.saveAll(tokens);
        } catch (Exception e) {
            throw new AppException(
                ErrorCode.INTERNAL_ERROR, 
                String.format("Failed to revoke refresh token in RefreshTokenService: %s", e.getMessage())
            );
        }
    }
}
