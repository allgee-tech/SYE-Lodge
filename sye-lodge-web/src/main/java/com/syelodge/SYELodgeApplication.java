package com.syelodge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * SYE Lodge entry point.
 *
 * <p>The application class lives in the root {@code com.syelodge} package so
 * component scanning reaches the service and web modules.</p>
 */
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@ConfigurationPropertiesScan("com.syelodge.service.config")
@EntityScan("com.syelodge.persistence.entity")
@EnableJpaRepositories("com.syelodge.persistence.repository")
public class SYELodgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SYELodgeApplication.class, args);
    }
}
