package com.swd.bike.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    private static final String API_VERSION = "v1";

    private static final String API_TITLE = "Bike API";

    private static final String API_DESCRIPTION = "Bike API";

    public final static String BEARER_SCHEME = "Bearer";

    @Value("${server.host}")
    String serverUrl;

    @Bean
    public OpenAPI openAPiConfig() {
        Server server = new Server();
        server.setUrl(serverUrl);
        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name(BEARER_SCHEME)));
    }

}
