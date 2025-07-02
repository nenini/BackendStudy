package com.example.day4_project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title="Day4 Project API 문서",
        version="v1.0",
        description="게시글 및 사용자 관리 API 명세서"
    ), servers = @Server(url="http://localhost:8080", description = "로컬 서버")
)
public class SwaggerConfig {
}
