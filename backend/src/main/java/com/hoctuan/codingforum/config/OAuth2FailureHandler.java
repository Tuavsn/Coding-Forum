package com.hoctuan.codingforum.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.hoctuan.codingforum.constant.AppConstant;

import java.io.IOException;

@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final AppConstant appConstant;
    private final Encoder encoder;

    public OAuth2FailureHandler(AppConstant appConstant, Encoder encoder) {
        this.appConstant = appConstant;
        this.encoder = encoder;
    }

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException, ServletException {
        String targetUrl = this.appConstant.getClientUrl() + "/oauth2/redirect";
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("status", HttpStatus.UNAUTHORIZED.value())
            .queryParam("message", encoder.encode("Quá trình xác thực đã thất bại! Vui lòng thử lại!"))
            .build().toUriString();
        response.sendRedirect(targetUrl);
    }
}
