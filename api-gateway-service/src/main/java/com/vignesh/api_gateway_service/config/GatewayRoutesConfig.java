package com.vignesh.api_gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("patient-svc", r-> r
                        .path("**/patients/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://patient-service"))
                .route("appointment-svc",r->r
                        .path("**/appointment/**")
                        .filters(f->f.stripPrefix(1))
                        .uri("lb://appointment-service"))
                .build();
    }
}
