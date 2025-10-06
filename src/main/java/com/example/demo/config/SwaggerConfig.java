package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Swagger + MySQL Demo API",
                version = "1.0",
                description = "User Register & Login with Database"
        )
)
public class SwaggerConfig {
}
