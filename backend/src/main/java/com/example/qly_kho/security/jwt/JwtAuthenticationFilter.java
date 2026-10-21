package com.example.qly_kho.security.jwt;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.qly_kho.constant.AppConstants;
import com.example.qly_kho.exception.custom.UnauthorizedException;
import com.example.qly_kho.security.cache.UserCache;
import com.example.qly_kho.security.cache.UserCacheService;
import com.example.qly_kho.security.cache.UserVersionCache;
import com.example.qly_kho.security.jwt.payload.AccessTokenPayload;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtProvider jwtProvider;
    private final UserCacheService userCacheService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                try {
                    
                    String token = getTokenFromRequest(request);

                    if(StringUtils.hasText(token)) {

                        AccessTokenPayload tokenPayload = jwtProvider.parseAccessToken(token);
                        
                        String username = tokenPayload.username();
                        Long tokenAuthVersion = tokenPayload.authVersion();
                        Long tokenPermissionVersion = tokenPayload.permissionVersion();
                        UserVersionCache userVersionCache = userCacheService.getUserVersionCache(username);
                        
                        if (userVersionCache == null ||
                            !Objects.equals(userVersionCache.authVersion(), tokenAuthVersion) || 
                            !Objects.equals(userVersionCache.permissionVersion(), tokenPermissionVersion)) {


                            throw new UnauthorizedException("Token outdated in JwtAuthenticationFilter");
                        }

                        UserCache userCache = userCacheService.getUserCacheByUsername(
                            username, 
                            userVersionCache.authVersion(), 
                            userVersionCache.permissionVersion()
                        );
                        
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userCache,
                            null, 
                            userCache.getGrantedAuthorities()
                        );

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (UnauthorizedException e) {

                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                    return;
                } catch (Exception e) {

                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");

                    response.getWriter().write("""
                    {
                        "status": 401,
                        "code": "JWT_ERROR",
                        "message": "Token không hợp lệ"
                    }
                    """);
                    return;
                }
                filterChain.doFilter(request, response);

    } 


    private String getTokenFromRequest(HttpServletRequest request)  {
        
        String bearerToken = request.getHeader(AppConstants.JWT_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(AppConstants.JWT_PREFIX)) {
            return bearerToken.substring(AppConstants.JWT_PREFIX.length());
        }

        return null;
    }
}
