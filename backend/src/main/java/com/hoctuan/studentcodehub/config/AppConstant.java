package com.hoctuan.studentcodehub.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@Data
public class AppConstant {
    @Value("${client.url}")
    private String clientUrl;

    @Value("${spring.jwt.rsa.public-key}")
    private RSAPublicKey publicKey;
    @Value("${spring.jwt.rsa.private-key}")
    private RSAPrivateKey privateKey;
    @Value("${spring.cookie.expires-time}")
    private Integer expiresTime;

    @Value("${bcrypt.log-rounds}")
    private Integer logRounds;
}
