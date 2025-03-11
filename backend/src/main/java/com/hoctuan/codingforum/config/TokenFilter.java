package com.hoctuan.codingforum.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.service.token.TokenService;

import java.io.IOException;

@Component
@Order(1)
public class TokenFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(7);
        if(!tokenService.validateToken(token)) {
            throw new CustomException("Access Token không hợp lệ, vui lòng đăng nhập lại", HttpStatus.UNAUTHORIZED.value());
        }
        filterChain.doFilter(request, response);
    }

}
