package com.hoctuan.studentcodehub.service.account;

import com.hoctuan.studentcodehub.model.dto.auth.AuthRequestDTO;
import com.hoctuan.studentcodehub.model.dto.auth.AuthResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO register(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO forgotPassword(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO getInfo();

    public void logout();
}
