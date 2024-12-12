package com.hoctuan.codingforum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import com.hoctuan.codingforum.constant.AppConstant;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig {
    @Autowired
    private AppConstant appConstant;
    @Autowired
    private OAuth2SuccessHandler successHandler;
    @Autowired
    private OAuth2FailureHandler failureHandler;
    @Autowired
    private DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    private String[] whiteList = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/auth/**",
            "/api/oauth2/**",
            "/oauth2/**",
    };

    private String[] getWhiteList = {
            "/api/topic/**",
            "/api/post/**",
            "/api/comment/**",
            "/api/user/**"
    };


    @Bean
    Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new CustomAuthenticationConverter());
        return jwtConverter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(whiteList).permitAll();
                    auth.requestMatchers(HttpMethod.GET, getWhiteList).permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(auth -> {
                    auth.authorizationEndpoint(
                            point -> point.baseUri("/oauth2/authorize")
                    );
                    auth.redirectionEndpoint(
                            redirect -> redirect.baseUri("/oauth2/callback/*")
                    );
                    auth.successHandler(successHandler);
                    auth.failureHandler(failureHandler);
                })
                .oauth2ResourceServer(oauth2 -> {
                    oauth2
                            .authenticationEntryPoint(delegatedAuthenticationEntryPoint)
                            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));
                })
                .exceptionHandling(handler -> handler.authenticationEntryPoint(delegatedAuthenticationEntryPoint))
                .build();
    }
}
