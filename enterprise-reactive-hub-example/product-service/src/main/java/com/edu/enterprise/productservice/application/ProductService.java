package com.edu.enterprise.productservice.application;

import com.edu.enterprise.productservice.domain.model.Product;
import com.edu.enterprise.productservice.domain.repository.ProductRepository;
import com.edu.enterprise.productservice.dto.ProductRequest;
import com.edu.enterprise.productservice.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductEventPublisher productEventPublisher;

    public Flux<ProductResponse> findAll() {
        return repository.findAll()
                .map(this::toResponse) // Transforma cada producto
                //.delayElements(Duration.ofMillis(100)) // Simula latencia
                .switchIfEmpty(Flux.error(new RuntimeException("No hay productos")));
    }

    public Mono<ProductResponse> save(ProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .build();

        return repository.save(product)
                .map(this::toResponse)
                .flatMap(response ->
                        productEventPublisher.publishProductCreated(response)
                                .thenReturn(response)
                )
                ;
    }

    public Mono<ProductResponse> findById(Integer id) {
        return repository.findById(id)
                .map(this::toResponse);
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice()
        );
    }
}