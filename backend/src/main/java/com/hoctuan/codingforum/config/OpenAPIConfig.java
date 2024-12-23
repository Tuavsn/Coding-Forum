package com.hoctuan.codingforum.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hoctuan.codingforum.constant.AppConstant;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Autowired
    private AppConstant appConstant;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(
                new Info().title(appConstant.getTitle())
                    .version(appConstant.getVersion())
                    .description(appConstant.getDescription())
            ).servers(
                List.of(
                    new Server()
                            .url(appConstant.getHostServerUrl())
                            .description("Host Server"),
                    new Server()
                            .url(appConstant.getLocalServerUrl())
                            .description("Local Server")
                )
            ).components(
                new Components().addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                )
            ).security(
                List.of(
                        new SecurityRequirement().addList("bearerAuth")
                )
            );
    }
}
