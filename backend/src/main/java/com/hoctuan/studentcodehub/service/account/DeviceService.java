package com.hoctuan.studentcodehub.service.account;

import com.hoctuan.studentcodehub.model.dto.device.DeviceResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface DeviceService {
    public void add(String userId, String token, HttpServletRequest request, LocalDateTime expireAt, LocalDateTime lastLoginTime);

    public void revoke(String deviceId, HttpServletRequest request);

    public Page<DeviceResponseDTO> getAllByUserId(Pageable pageable);
}
