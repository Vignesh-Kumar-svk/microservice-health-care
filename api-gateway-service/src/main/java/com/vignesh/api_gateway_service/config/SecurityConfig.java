package com.vignesh.api_gateway_service.config;

import com.vignesh.api_gateway_service.filter.JwtValidationGatewayFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){
        logger.info("Inside security filter chain - SecurityConfig INITIALIZED");
        httpSecurity.csrf(csrf ->csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers("/actuator/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyExchange().authenticated())
//                .oauth2Login(Customizer.withDefaults()) //For browser login
                .oauth2ResourceServer(oauth -> oauth.jwt(
                        Customizer.withDefaults()
                ));
        return httpSecurity.build();
    }
}
