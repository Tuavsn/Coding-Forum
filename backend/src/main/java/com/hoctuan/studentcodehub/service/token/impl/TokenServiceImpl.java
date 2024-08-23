package com.hoctuan.studentcodehub.service.token.impl;

import com.hoctuan.studentcodehub.config.AppConstant;
import com.hoctuan.studentcodehub.constant.AccountRole;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.service.account.DeviceService;
import com.hoctuan.studentcodehub.service.common.AuthContext;
import com.hoctuan.studentcodehub.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.google.common.hash.Hashing;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private AppConstant appConstant;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private DeviceService deviceService;
    @Qualifier("jwtDecoder")
    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public String buildToken(User user, HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusDays(appConstant.getExpiresTime());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now.atZone(ZoneId.systemDefault()).toInstant())
                .expiresAt(expiresAt.atZone(ZoneId.systemDefault()).toInstant())
                .subject(user.getId().toString())
                .claim("ROLE", user.getRole())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        deviceService.add(user.getId().toString(), hashString(token), request, expiresAt, now);

        return token;
    }

    @Override
    public boolean validateToken(String token) {
        User user = authContext.getUserAuthenticated();
        if(user == null) { return false; }
        if(!user.getRole().equals(AccountRole.USER)) { return true; }
        return user.getDevices().stream().anyMatch(
                d -> d.getToken().equals(hashString(token))
        );
    }

    @Override
    public boolean compareToken(String tokenHash, String tokenPlain) {
        return tokenHash.equals(hashString(tokenPlain));
    }

    private String hashString(String str) {
        return Hashing
                .sha256()
                .hashString(str, StandardCharsets.UTF_8)
                .toString();
    }
}
