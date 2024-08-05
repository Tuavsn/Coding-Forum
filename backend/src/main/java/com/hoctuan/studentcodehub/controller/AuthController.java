package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseResponse;
import com.hoctuan.studentcodehub.model.dto.auth.AuthRequestDTO;
import com.hoctuan.studentcodehub.service.account.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

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
}
