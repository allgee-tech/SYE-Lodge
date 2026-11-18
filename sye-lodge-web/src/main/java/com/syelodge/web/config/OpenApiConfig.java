package com.syelodge.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** OpenAPI metadata and the bearer-token authorisation scheme. */
@Configuration
public class OpenApiConfig {

    private static final String BEARER = "bearerAuth";

    @Bean
    public OpenAPI sye_lodgeOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SYE Lodge API")
                        .version("1.0.0")
                        .description("Student accommodation for Ghana. Browse approved listings, "
                                + "manage landlord listings and run administrator moderation. "
                                + "Direct contact only — SYE Lodge never processes rent payments.")
                        .contact(new Contact().name("SYE Lodge").email("support@sye_lodge.local")))
                .components(new Components().addSecuritySchemes(BEARER,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER));
    }
}
