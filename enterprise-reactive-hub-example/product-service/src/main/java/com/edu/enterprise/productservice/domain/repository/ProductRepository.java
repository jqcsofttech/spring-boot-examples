package com.edu.enterprise.productservice.domain.repository;

import com.edu.enterprise.productservice.domain.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    // Flux emite múltiples elementos de forma asíncrona
    Flux<Product> findByNameContaining(String name);
}