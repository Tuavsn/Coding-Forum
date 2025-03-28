package com.hoctuan.codingforum.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoctuan.codingforum.common.BaseResponse;
import com.hoctuan.codingforum.model.dto.auth.AuthRequestDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.service.account.AuthService;

@RestController
@RequestMapping("${spring.api.prefix}/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(
        @RequestBody @Valid AuthRequestDTO authRequestDTO,
        HttpServletRequest request
    ) {
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Đăng nhập thành công")
            .data(authService.login(authRequestDTO, request))
            .status(HttpStatus.OK.value())
            .build()
        , HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(
        @RequestBody @Valid AuthRequestDTO authRequestDTO,
        HttpServletRequest request
    ) {
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Đăng ký thành công")
            .data(authService.register(authRequestDTO, request))
            .status(HttpStatus.OK.value())
            .build()
        , HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse> logout() {
        authService.logout();
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Đăng xuất thành công")
            .data(null)
            .status(HttpStatus.OK.value())
            .build()
        , HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping("/profile")
    public ResponseEntity<BaseResponse> getProfile() {
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Lấy thông tin thành công")
            .data(authService.getInfo())
            .status(HttpStatus.OK.value())
            .build()
        , HttpStatus.OK);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<BaseResponse> updateProfile(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        authService.updateProfile(userRequestDTO);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Cập nhật thông tin thành công")
            .data(null)
            .status(HttpStatus.OK.value())
            .build()
        , HttpStatus.OK);
    }
}
