package com.hoctuan.codingforum.service.account.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoctuan.codingforum.constant.AccountAchievement;
import com.hoctuan.codingforum.constant.AccountRole;
import com.hoctuan.codingforum.constant.AccountStatus;
import com.hoctuan.codingforum.constant.AppConstant;
import com.hoctuan.codingforum.constant.AuthProvider;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.exception.NotFoundException;
import com.hoctuan.codingforum.model.dto.auth.AuthRequestDTO;
import com.hoctuan.codingforum.model.dto.auth.AuthResponseDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.service.account.AuthService;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.token.TokenService;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AppConstant appConstant;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthContext authContext;

    public AuthServiceImpl(AppConstant appConstant, UserRepository userRepository, TokenService tokenService,
            AuthContext authContext) {
        this.appConstant = appConstant;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authContext = authContext;
    }

    @Override
    @Transactional
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        String email = authRequestDTO.getEmail().toLowerCase();
        String password = authRequestDTO.getPassword();
        User user = userRepository.findByEmailAndAuthProvider(email, AuthProvider.LOCAL).orElse(null);
        return userRepository.findByEmailAndAuthProvider(
                authRequestDTO.getEmail(), AuthProvider.LOCAL).map(user -> {
                    if (user.getIsDeleted() || user.getStatus().equals(AccountStatus.INACTIVE)) {
                        throw new CustomException("Tài khoản của bạn đã bị xoá hoặc chưa được active",
                                HttpStatus.BAD_REQUEST.value());
                    }

                    if (BCrypt.checkpw(authRequestDTO.getPassword(), user.getPassword())) {
                        return AuthResponseDTO.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .username(user.getUsername())
                                .gender(user.getGender())
                                .role(user.getRole())
                                .avatar(user.getAvatar())
                                .achievement(user.getAchievement())
                                .address(user.getAddress())
                                .phone(user.getPhone())
                                .status(user.getStatus())
                                .token(tokenService.buildToken(user, request))
                                .createdAt(user.getCreatedAt())
                                .updatedAt(user.getUpdatedAt())
                                .createdBy(user.getCreatedBy())
                                .updatedBy(user.getUpdatedBy())
                                .build();
                    } else {
                        throw new CustomException("Tài khoản hoặc mật khẩu không chính xác",
                                HttpStatus.UNAUTHORIZED.value());
                    }
                }).orElseThrow(() -> new NotFoundException("Tài khoản hoặc mật khẩu không chính xác"));
    }

    @Override
    @Transactional
    public AuthResponseDTO register(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        authRequestDTO.setEmail(authRequestDTO.getEmail().toLowerCase());
        User user = userRepository.findByEmailAndAuthProvider(
                authRequestDTO.getEmail(), AuthProvider.LOCAL).orElse(null);

        if (user != null) {
            if (user.getIsDeleted()) {
                throw new CustomException("Tài khoản của bạn bị từ chối truy cập", HttpStatus.NOT_ACCEPTABLE.value());
            } else if (user.getStatus().equals(AccountStatus.INACTIVE)) {
                throw new CustomException("Tài khoản của bạn chưa được kích hoạt email",
                        HttpStatus.BAD_REQUEST.value());
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
                .avatar("https://res.cloudinary.com/ds75yhrpy/image/upload/v1725270804/9187604_kpfskc.png")
                .createdBy("System")
                .updatedBy("System")
                .build());

        return AuthResponseDTO.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .gender(savedUser.getGender())
                .role(savedUser.getRole())
                .avatar(savedUser.getAvatar())
                .achievement(savedUser.getAchievement())
                .totalSubmissionPoint(savedUser.getTotalSubmissionPoint())
                .address(savedUser.getAddress())
                .phone(savedUser.getPhone())
                .status(savedUser.getStatus())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .createdBy(savedUser.getCreatedBy())
                .updatedBy(savedUser.getUpdatedBy())
                .build();
    }

    @Override
    public AuthResponseDTO getInfo() {
        User user = authContext.getUserAuthenticated();
        return AuthResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .gender(user.getGender())
                .role(user.getRole())
                .avatar(user.getAvatar())
                .achievement(user.getAchievement())
                .totalSubmissionPoint(user.getTotalSubmissionPoint())
                .address(user.getAddress())
                .phone(user.getPhone())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .build();
    }

    @Override
    public AuthResponseDTO forgotPassword(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        return null;
    }

    @Override
    public void logout() {
        authContext.clearUserAuthenticated();
    }

    @Override
    public void updateProfile(UserRequestDTO userRequestDTO) {
        User user = authContext.getUserAuthenticated();

        user.setUsername(userRequestDTO.getUsername());
        user.setAvatar(userRequestDTO.getAvatar());
        user.setAddress(userRequestDTO.getAddress());
        user.setPhone(userRequestDTO.getPhone());
        user.setGender(userRequestDTO.getGender());
        if (userRequestDTO.getPassword() != null) {
            user.setPassword(userRequestDTO.getPassword());
        }

        userRepository.save(user);
    }

    private boolean validateUser(User user) {
        
    }
}
