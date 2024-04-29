package com.jh.weatherdiarypj.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
    
    private Info apiInfo() {
        return new Info()
                .title("날씨 일기 API")
                .description("날씨 일기 프로젝트의 Swagger API DOC")
                .version("1.0.0");
    }
}
