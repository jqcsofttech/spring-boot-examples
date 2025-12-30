package com.edu.enterprise.productservice.config;

import com.edu.enterprise.productservice.infrastructure.adapters.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductHandler handler) {
        return route()
                .GET("/api/v1/products", handler::getAllProducts)
                .POST("/api/v1/products", handler::createProduct)
                .build();
    }
}