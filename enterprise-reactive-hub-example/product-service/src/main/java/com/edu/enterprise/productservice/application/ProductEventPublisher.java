package com.edu.enterprise.productservice.application;

import com.edu.enterprise.productservice.constants.AppConstants;
import com.edu.enterprise.productservice.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ProductEventPublisher {

    private  final KafkaSender<String, String> kafkaSender;
    private final ObjectMapper objectMapper; // El conversor de Jackson

    public Mono<Void> publishProductCreated(ProductResponse product) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(product))
                .flatMap(json -> {
                    SenderRecord<String, String, String> record = SenderRecord.create(
                            new ProducerRecord<>(AppConstants.PRODUCT_TOPIC, product.id().toString(), json),
                            product.id().toString()
                    );
                    return kafkaSender.send(Mono.just(record))
                            .doOnNext(r -> System.out.println("Enviado con éxito: " + json))
                            .next()
                            .then();
                })
                .onErrorResume(e -> {
                    System.err.println("Error serializando: " + e.getMessage());
                    return Mono.empty();
                });
    }
}
