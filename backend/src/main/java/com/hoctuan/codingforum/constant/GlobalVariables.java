package com.hoctuan.codingforum.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Configuration
@Getter
public class GlobalVariables {
    @Value("${client.url:codingforum.trinhhoctuan.io.vn}")
    public String clientUrl;
    @Value("${server.url:codingforumapi.trinhhoctuan.io.vn}")
    public String serverUrl;

    public static String CLIENT_URL;
    public static String SERVER_URL;

    @PostConstruct
    private void init() {
        CLIENT_URL = clientUrl;
        SERVER_URL = serverUrl;
    }
}
