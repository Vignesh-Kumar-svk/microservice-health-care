package com.vignesh.api_gateway_service.config;

import com.vignesh.api_gateway_service.filter.JwtValidationGatewayFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    private final JwtValidationGatewayFilterFactory jwtFilter;

    public GatewayRoutesConfig(JwtValidationGatewayFilterFactory jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder routeLocatorBuilder){

        Logger logger = LoggerFactory.getLogger(GatewayRoutesConfig.class);
        logger.info("Inside gateway custom routes");
        return routeLocatorBuilder.routes()
               // .route("paymentId", r->r.path("/payment/**").uri("http://localhost:9009")) --> Static Routing
                .route("patient-svc", r-> r  //(Protected)
                        .path("/patients/**")
//                        .filters(f -> f.stripPrefix(1))
                        .filters(f->f.filter(jwtFilter.apply(new JwtValidationGatewayFilterFactory.Config())))
                        .uri("lb://patient-service")) // -> Dynamic Routing
                .route("appointment-svc",r->r // (Protected)
                        .path("/appointment/**")
//                        .filters(f->f.stripPrefix(1))
                        .filters(f->f.filter(jwtFilter.apply(new JwtValidationGatewayFilterFactory.Config())))
                        .uri("lb://appointment-service"))
                .route("auth-svc", r->r   // Auth Service (public)
                        .path("/auth/**")
//                        .filters(f->f.stripPrefix(1))
                        .uri("lb://auth-service"))
                .route("patient-docs", r -> r
                        .path("/patient-docs/v3/api-docs")
                        .filters(f -> f.setPath("/v3/api-docs")) // inside patient-service
                        .uri("lb://patient-service"))
                .build();

    }

}
