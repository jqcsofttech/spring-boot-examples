package com.edu.enterprise.productservice.infrastructure.adapters;

import com.edu.enterprise.productservice.application.ProductService;
import com.edu.enterprise.productservice.dto.ProductRequest;
import com.edu.enterprise.productservice.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Retorna un flujo de datos (0..N)
    // Produces TEXT_EVENT_STREAM_VALUE permite ver los datos conforme llegan
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductResponse> getAllProducts() {
        return productService.findAll();
    }

    // Retorna un único resultado (0..1) envuelto en un Mono
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        return productService.save(request);
    }

    @GetMapping("/{id}")
    public Mono<ProductResponse> getProductById(@PathVariable Integer id) {
        return productService.findById(id);
    }
}