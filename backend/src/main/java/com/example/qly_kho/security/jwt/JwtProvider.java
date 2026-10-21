package com.example.qly_kho.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.qly_kho.config.JwtProperties;
import com.example.qly_kho.exception.custom.UnauthorizedException;
import com.example.qly_kho.security.jwt.payload.AccessTokenPayload;
import com.example.qly_kho.security.jwt.payload.RefreshTokenPayLoad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
    
    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(Authentication authentication, Long version) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.accessExpire());

        return Jwts.builder()
                .setSubject(username)
                .claim("version", version)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }    


    public String generateAccessTokenFromUsername(String username, Long authVersion, Long permissionVersion) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.accessExpire());

        return Jwts.builder()
            .setSubject(username)
            .claim("authVersion", authVersion)
            .claim("permissionVersion", permissionVersion)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
    }

    public String generateRefreshToken(String username, Long authVersion) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.refreshExpire());

        return Jwts.builder()
                .setSubject(username)
                .claim("authVersion", authVersion)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(
                String.format("JWT token has expired in JwtProvider : %s", e.getMessage())
            );
        } catch (JwtException e) {
            throw new UnauthorizedException(
                String.format("JWT token is invalid in JwtProvider : %s", e.getMessage())
            );
        }
    }

    public String getUsernameFromToken(String token) {

        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
        
    }

    public AccessTokenPayload parseAccessToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        
        return new AccessTokenPayload(
            claims.getSubject(),
            claims.get("authVersion", Long.class),
            claims.get("permissionVersion", Long.class)
        );
    }

    public RefreshTokenPayLoad parseRefreshToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        
        return new RefreshTokenPayLoad(
            claims.getSubject(),
            claims.get("authVersion", Long.class)
        );
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // ✅ đúng logic
        }
    }

    public long getAccessExpiration() {
        return jwtProperties.accessExpire();
    }
    public long getRefreshExpiration() {
        return jwtProperties.refreshExpire();
    }
}
