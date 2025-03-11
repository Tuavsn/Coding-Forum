package com.hoctuan.codingforum.service.common.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.service.common.AuthContext;

import java.util.Optional;

@Service
public class AuthContextImpl implements AuthContext {
    /**
     * Get current authenticated user's id
     * 
     * @return {UserId} | {non-null}
     */
    @Override
    public Optional<String> getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(ExtractPrincipal(authentication));
    }

    /**
     * Clear current authenticated user
     */
    @Override
    public void clearUserAuthenticated() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /**
     * Extract Principal from Security Context
     */
    private String ExtractPrincipal(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else if (principal instanceof Jwt) {
                return ((Jwt) principal).getSubject();
            } else if (principal instanceof String) {
                return principal.toString();
            }
        }
        return null;
    }
}
