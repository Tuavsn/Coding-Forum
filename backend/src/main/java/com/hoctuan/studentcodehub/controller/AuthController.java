package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseResponse;
import com.hoctuan.studentcodehub.service.account.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    
}
