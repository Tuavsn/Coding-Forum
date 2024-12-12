package com.hoctuan.codingforum.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public final class GlobalVariables {
    @Value("${client.url:codingforum.trinhhoctuan.io.vn}")
    public static String CLIENT_URL;
    @Value("${server.url:codingforumapi.trinhhoctuan.io.vn}")
    public static String SERVER_URL;
}
