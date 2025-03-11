package com.hoctuan.codingforum.config;

import com.cloudinary.Cloudinary;
import com.hoctuan.codingforum.constant.AppConstant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private final AppConstant appConstant;

    public CloudinaryConfig(AppConstant appConstant) {
        this.appConstant = appConstant;
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", appConstant.getCloudName());
        config.put("api_key", appConstant.getApiKey());
        config.put("api_secret", appConstant.getApiSecret());
        return new Cloudinary(config);
    }
}
