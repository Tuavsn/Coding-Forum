package com.hoctuan.studentcodehub.service.account;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

public interface DeviceService {
    public void add(String userId, String token, HttpServletRequest request, LocalDateTime expireAt, LocalDateTime lastLoginTime);

    public void revoke(String deviceId, HttpServletRequest request);
}
