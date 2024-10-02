package com.example.spring_boot;

import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.annotation.PostConstruct;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class OpenApiConfig {

    @PostConstruct
    public void init() {
        System.out.println("OpenApiConfig has been initialized");
    }

    @Bean
    public OpenApiCustomizer customOpenApi() {
        return openApi -> {
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("openapi.yaml")) {
                if (is != null) {
                    OpenAPI customOpenAPI = Yaml.mapper().readValue(is, OpenAPI.class);
                    openApi.setOpenapi(customOpenAPI.getOpenapi());
                    openApi.setInfo(customOpenAPI.getInfo());
                    openApi.setPaths(customOpenAPI.getPaths());
                    openApi.setComponents(customOpenAPI.getComponents());
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to load custom OpenAPI definition from YAML", e);
            }
        };
    }
}

