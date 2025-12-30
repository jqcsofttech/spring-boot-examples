package com.edu.enterprise.productservice.infrastructure.adapters;

import com.edu.enterprise.productservice.application.ProductService;
import com.edu.enterprise.productservice.dto.ProductRequest;
import com.edu.enterprise.productservice.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService service;

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse.ok()
                .body(service.findAll(), ProductResponse.class);
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequest.class)
                .flatMap(service::save)
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }
}
