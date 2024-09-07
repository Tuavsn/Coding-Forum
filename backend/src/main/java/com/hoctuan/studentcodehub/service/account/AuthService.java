package com.hoctuan.studentcodehub.service.account;

import com.hoctuan.studentcodehub.model.dto.auth.AuthRequestDTO;
import com.hoctuan.studentcodehub.model.dto.auth.AuthResponseDTO;
import com.hoctuan.studentcodehub.model.dto.user.UserRequestDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO register(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO forgotPassword(AuthRequestDTO authRequestDTO, HttpServletRequest request);

    public AuthResponseDTO getInfo();

    public void updateProfile(UserRequestDTO userRequestDTO);

    public void logout();
}
