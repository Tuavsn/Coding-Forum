package com.hoctuan.codingforum.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.hoctuan.codingforum.constant.AccountAchievement;
import com.hoctuan.codingforum.constant.AccountRole;
import com.hoctuan.codingforum.constant.AccountStatus;
import com.hoctuan.codingforum.constant.AppConstant;
import com.hoctuan.codingforum.constant.AuthProvider;
import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.constant.TokenType;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.service.token.TokenService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2FailureHandler OAuth2FailureHandler;
    private final AppConstant appConstant;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final Encoder encoder;

    public OAuth2SuccessHandler(AppConstant appConstant, UserRepository userRepository, TokenService tokenService,
            Encoder encoder, OAuth2FailureHandler OAuth2FailureHandler) {
        this.appConstant = appConstant;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.OAuth2FailureHandler = OAuth2FailureHandler;
    }

    private User user = null;
    private String message = "Login successful";
    private Integer status = HttpStatus.OK.value();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String targetUrl = appConstant.getClientUrl() + "/oauth2/redirect";
        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();
        String name = attributes.getOrDefault("name", "").toString();
        String email = attributes.getOrDefault("email", "").toString();
        String avatar = attributes.getOrDefault("picture", "").toString();
        this.userRepository.findByEmail(email).ifPresentOrElse(user -> {
            // Check if account has been deleted
            if (user.getIsDeleted()) {
                this.message = ErrorCode.IS_DELETED_ACCOUNT.getMessage();
                this.status = ErrorCode.IS_DELETED_ACCOUNT.getHttpStatus().value();
                // Check if account has been blocked
            } else if (user.getStatus().equals(AccountStatus.BLOCK)) {
                this.message = ErrorCode.ACCESS_DENIED.getMessage();
                this.status = ErrorCode.ACCESS_DENIED.getHttpStatus().value();
            } else {
                // Check if account already resgiter with username and password
                if (user.getAuthProvider().equals(AuthProvider.LOCAL)) {
                    user.setAuthProvider(AuthProvider.LOCAL_AND_GOOGLE);
                    this.userRepository.save(user);
                }
                this.user = user;
            }
            // Save new account
        }, () -> {
            this.user = this.userRepository.save(
                    User.builder()
                            .email(email)
                            .username(name)
                            .avatar(avatar)
                            .role(AccountRole.USER)
                            .achievement(AccountAchievement.BEGINNER)
                            .status(AccountStatus.ACTIVE)
                            .isDeleted(false)
                            .authProvider(AuthProvider.GOOGLE)
                            .password(BCrypt.hashpw(UUID.randomUUID().toString(),
                                    BCrypt.gensalt(appConstant.getLogRounds())))
                            .build());
        });
        this.message = this.encoder.encode(this.message);
        if (this.user != null) {
            String accessToken = tokenService.buildToken(user, TokenType.ACCESS_TOKEN, request);
            String refreshToken = tokenService.buildToken(user, TokenType.REFRESH_TOKEN, request);
            // Write refreshToken to cookie
            ResponseCookie responseCookie = ResponseCookie.from(TokenType.REFRESH_TOKEN.getName(), refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(appConstant.getRefreshTokenExpireTime())
                .build();
            response.setHeader("Set-Cookie", responseCookie.toString());
            // Build redirect return params
            targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("status", this.status)
                    .queryParam("message", this.message)
                    .queryParam("token", accessToken)
                    .queryParam("userId", this.user.getId())
                    .queryParam("username", this.encoder.encode(this.user.getUsername()))
                    // .queryParam("avatar", this.encoder.encode(this.user.getAvatar()))
                    .queryParam("role", this.user.getRole())
                    .queryParam("achievement", this.user.getAchievement())
                    .build().toUriString();
            // Set user'authentication to SecurityContextHodler
            Authentication auth = new UsernamePasswordAuthenticationToken(this.user.getEmail(), null, authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("User {} Login successful", this.user.getEmail());
        } else {
            targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("status", this.status)
                    .queryParam("message", this.message)
                    .build().toUriString();
            log.error("User Login failure");
        }
        response.sendRedirect(targetUrl);
    }
}
