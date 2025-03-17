package com.hoctuan.codingforum.service.token.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.google.common.hash.Hashing;
import com.hoctuan.codingforum.constant.AppConstant;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.service.account.DeviceService;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.token.TokenService;

@Service
public class TokenServiceImpl implements TokenService {
    private final AppConstant appConstant;
    private final JwtEncoder jwtEncoder;
    private final AuthContext authContext;
    private final DeviceService deviceService;
    @Qualifier("jwtDecoder")
    private final JwtDecoder jwtDecoder;

    public TokenServiceImpl(AppConstant appConstant, JwtEncoder jwtEncoder, AuthContext authContext,
            DeviceService deviceService, JwtDecoder jwtDecoder) {
        this.appConstant = appConstant;
        this.jwtEncoder = jwtEncoder;
        this.authContext = authContext;
        this.deviceService = deviceService;
        this.jwtDecoder = jwtDecoder;
    }

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

    // @Override
    // public boolean validateToken(String token) {
    //     UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
    //             .orElseThrow(() -> new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value())));
    //     User user = userRepository.findById();
    //     if (user == null) {
    //         return false;
    //     }
    //     if (!user.getRole().equals(AccountRole.USER)) {
    //         return true;
    //     }
    //     return user.getDevices().stream().anyMatch(
    //             d -> d.getToken().equals(hashString(token)));
    // }

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
