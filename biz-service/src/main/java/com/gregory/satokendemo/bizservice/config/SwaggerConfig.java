package com.gregory.satokendemo.bizservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

  private static final String SECURITY_SCHEME = "bearerAuth";

  @Bean
  public OpenAPI customOpenApi() {

    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes(SECURITY_SCHEME, createOAuthScheme()))
        .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME));
  }

  private SecurityScheme createOAuthScheme() {

    return new SecurityScheme()
        .type(Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");
  }
}
