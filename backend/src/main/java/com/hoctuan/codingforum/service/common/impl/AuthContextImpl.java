package com.hoctuan.codingforum.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.exception.NotFoundException;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.service.common.AuthContext;

import java.util.UUID;

@Service
public class AuthContextImpl implements AuthContext {
    @Autowired
    private UserRepository userRepository;

    /**
     * Get current authenticated user's id
     * @return {UserId} | {null}
     */
    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
            return principal.toString();
        }
        return null;
    }

    /**
     * Get current authenticated user's info
     * @return {User}
     */
    @Override
    public User getCurrentUserDetails() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
    }

    /**
     * Clear current authenticated user
     */
    @Override
    public void clearUserAuthenticated() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
