package com.hoctuan.studentcodehub.service.token;

import com.hoctuan.studentcodehub.model.entity.account.User;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {
    public String buildToken(User user, HttpServletRequest request);
    public boolean validateToken(String token);
    public boolean compareToken(String tokenHash, String tokenPlain);
}
