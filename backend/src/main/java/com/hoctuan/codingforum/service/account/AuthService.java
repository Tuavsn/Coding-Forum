package com.hoctuan.codingforum.service.account;

import com.hoctuan.codingforum.model.dto.auth.AuthRequestDTO;
import com.hoctuan.codingforum.model.dto.auth.AuthResponseDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO register(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO forgotPassword(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO getInfo();

    public void updateProfile(UserRequestDTO userRequestDTO);

    public void logout();
}
