package com.hoctuan.codingforum.service.common.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.exception.NotFoundException;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.service.common.AuthContext;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthContextImpl implements AuthContext {
    private final UserRepository userRepository;

    @Override
    public User getUserAuthenticated() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
    }

    @Override
    public void clearUserAuthenticated() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
