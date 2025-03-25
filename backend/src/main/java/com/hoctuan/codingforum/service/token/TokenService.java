package com.hoctuan.codingforum.service.token;


import com.hoctuan.codingforum.constant.TokenType;
import com.hoctuan.codingforum.model.entity.account.User;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {
    public String buildToken(User user, TokenType type, HttpServletRequest request);

    // public boolean validateToken(String token);
    
    public boolean compareToken(String tokenHash, String tokenPlain);
}
