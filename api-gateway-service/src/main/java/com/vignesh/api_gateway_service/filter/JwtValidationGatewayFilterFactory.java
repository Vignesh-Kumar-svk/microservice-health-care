package com.vignesh.api_gateway_service.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtValidationGatewayFilterFactory.Config> {

    Logger logger = LoggerFactory.getLogger(JwtValidationGatewayFilterFactory.class);
    private final WebClient webClient;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    public static class Config {}

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public GatewayFilter apply(Config config) {

        logger.info("authService url----------> {}",authServiceUrl);

        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(token==null || !token.startsWith("Bearer "))
            {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    .uri(authServiceUrl + "/auth/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    // STEP 1: Catch 4xx (400–499) from auth-service
                    .onStatus(
                            status -> status.is4xxClientError(),
                            response -> Mono.error(new RuntimeException("INVALID_TOKEN"))
                    )

                    // STEP 2: Catch 5xx (500–599) from auth-service
                    .onStatus(
                            status -> status.is5xxServerError(),
                            response -> Mono.error(new RuntimeException("AUTH_SERVICE_DOWN"))
                    )

                    .toBodilessEntity()
                    // STEP 3: If token valid -> continue filter chain
                    .flatMap(res -> chain.filter(exchange))

                    .onErrorResume(ex -> {
                        if ("INVALID_TOKEN".equals(ex.getMessage())) {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);   // 401
                        } else if ("AUTH_SERVICE_DOWN".equals(ex.getMessage())) {
                            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE); // 503
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR); // 500 fallback
                        }
                        return exchange.getResponse().setComplete();
                    });

        };
    }
}
