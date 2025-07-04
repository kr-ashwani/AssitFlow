package com.github.krashwani.assitflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class JpaAuditorAwareConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("SYSTEM");

        // from security context you can get user name
        //return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        //        .map(Authentication::getName);
    }

}
