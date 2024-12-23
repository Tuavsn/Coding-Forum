package com.hoctuan.codingforum.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.hoctuan.codingforum.constant.AccountRole;
import com.hoctuan.codingforum.constant.AccountStatus;
import com.hoctuan.codingforum.constant.AppConstant;
import com.hoctuan.codingforum.constant.AuthProvider;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.service.token.TokenService;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private AppConstant appConstant;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Encoder encoder;

    private User user = null;
    private String message = "Đăng nhập thành công";
    private Integer status = HttpStatus.OK.value();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        String targetUrl = appConstant.getClientUrl() + "/oauth2/redirect";

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();
        String name = attributes.getOrDefault("name", "").toString();
        String email = attributes.getOrDefault("email", "").toString();

        this.userRepository.findByEmailAndAuthProvider(email, AuthProvider.GOOGLE).ifPresentOrElse(user -> {
            if (user.getIsDeleted() || user.getStatus().equals(AccountStatus.BLOCK)) {
                if (user.getIsDeleted()) {
                    this.message = "Tài khoản của bạn đã bị xóa khỏi hệ thống";
                    this.status = HttpStatus.NOT_FOUND.value();
                } else {
                    this.message = "Tài khoản của bạn đang bị khóa";
                    this.status = HttpStatus.FORBIDDEN.value();
                }
            } else {
                this.user = user;
            }
        }, () -> {
            User user = new User();
            user.setAuthProvider(AuthProvider.GOOGLE);
            user.setEmail(email);
            user.setUsername(name);
            user.setRole(AccountRole.USER);
            this.user = this.userRepository.save(user);
        });
        this.message = this.encoder.encode(this.message);
        if (this.user != null) {
            targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("status", this.status)
                    .queryParam("message", this.message)
                    .queryParam("token", this.tokenService.buildToken(this.user, request))
                    .queryParam("username", this.encoder.encode(this.user.getUsername()))
                    .queryParam("role", this.user.getRole())
                    .build().toUriString();
        } else {
            targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("status", this.status)
                    .queryParam("message", this.message)
                    .build().toUriString();
        }
        response.sendRedirect(targetUrl);
    }
}
