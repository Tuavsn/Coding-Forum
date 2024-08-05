package com.hoctuan.studentcodehub.service.account.impl;

import com.hoctuan.studentcodehub.config.AppConstant;
import com.hoctuan.studentcodehub.constant.AccountAchievement;
import com.hoctuan.studentcodehub.constant.AccountRole;
import com.hoctuan.studentcodehub.constant.AccountStatus;
import com.hoctuan.studentcodehub.constant.AuthProvider;
import com.hoctuan.studentcodehub.exception.CustomException;
import com.hoctuan.studentcodehub.exception.NotFoundException;
import com.hoctuan.studentcodehub.model.dto.auth.AuthRequestDTO;
import com.hoctuan.studentcodehub.model.dto.auth.AuthResponseDTO;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.repository.account.UserRepository;
import com.hoctuan.studentcodehub.service.account.AuthService;
import com.hoctuan.studentcodehub.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AppConstant appConstant;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        authRequestDTO.setEmail(authRequestDTO.getEmail().toLowerCase());

        return userRepository.findByEmailAndAuthProvider(
                authRequestDTO.getEmail(), AuthProvider.LOCAL
        ).map(user -> {
            if(user.getIsDeleted() || user.getStatus().equals(AccountStatus.INACTIVE)) {
                throw new CustomException("Tài khoản của bạn đã bị xoá hoặc chưa được active", HttpStatus.BAD_REQUEST.value());
            }

            if(BCrypt.checkpw(authRequestDTO.getPassword(), user.getPassword())) {
                return AuthResponseDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole())
                        .token(tokenService.buildToken(user, request))
                        .createdAt(user.getCreatedAt())
                        .updatedAt(user.getUpdatedAt())
                        .createdBy(user.getCreatedBy())
                        .updatedBy(user.getUpdatedBy())
                        .build();
            } else {
                throw new CustomException("Tài khoản hoặc mật khẩu không chính xác", HttpStatus.UNAUTHORIZED.value());
            }
        }).orElseThrow(() -> new NotFoundException("Tài khoản hoặc mật khẩu không chính xác"));
    }

    @Override
    @Transactional
    public AuthResponseDTO register(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        authRequestDTO.setEmail(authRequestDTO.getEmail().toLowerCase());
        User user = userRepository.findByEmailAndAuthProvider(
                authRequestDTO.getEmail(), AuthProvider.LOCAL
        ).orElse(null);

        if(user != null) {
            if(user.getIsDeleted()) {
                throw new CustomException("Tài khoản của bạn bị từ chối truy cập", HttpStatus.NOT_ACCEPTABLE.value());
            } else if(user.getStatus().equals(AccountStatus.INACTIVE)) {
                throw new CustomException("Tài khoản của bạn chưa được kích hoạt email", HttpStatus.BAD_REQUEST.value());
            } else {
                throw new CustomException("Email đăng ký tài khoản đã tồn tại", HttpStatus.BAD_REQUEST.value());
            }
        }

        User savedUser = userRepository.saveAndFlush(User.builder()
                .email(authRequestDTO.getEmail())
                .username(authRequestDTO.getUsername())
                .password(BCrypt.hashpw(authRequestDTO.getPassword(), BCrypt.gensalt(appConstant.getLogRounds())))
                .authProvider(AuthProvider.LOCAL)
                .status(AccountStatus.ACTIVE)
                .role(AccountRole.USER)
                .achievement(AccountAchievement.BEGINNER)
                .isDeleted(false)
                .createdBy("System")
                .updatedBy("System")
                .build());

        return AuthResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .createdBy(savedUser.getCreatedBy())
                .updatedBy(savedUser.getUpdatedBy())
                .build();
    }

    @Override
    public AuthResponseDTO forgotPassword(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        return null;
    }

    @Override
    public void logout(String token) {

    }
}
