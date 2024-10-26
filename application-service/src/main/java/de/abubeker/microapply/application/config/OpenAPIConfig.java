package de.abubeker.microapply.application.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI applicationServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Application Service API")
                        .description("This is the REST API for Application Service")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")));
    }
}