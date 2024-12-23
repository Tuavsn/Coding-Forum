package com.hoctuan.codingforum.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@Data
public class AppConstant {
    @Value("${open.api.title}")
    private String title;
    @Value("${open.api.version}")
    private String version;
    @Value("${open.api.description}")
    private String description;
    @Value("${open.api.local-server-url}")
    private String localServerUrl;
    @Value("${open.api.host-server-url}")
    private String hostServerUrl;

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

    @Value("${spring.cloudinary.cloud-name")
    private String cloudName;
    @Value("${spring.cloudinary.api-key}")
    private String apiKey;
    @Value("${spring.cloudinary.api-secret")
    private String apiSecret;
}
