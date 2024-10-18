package com.nisum.reactive.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Service
@Slf4j
public class ReactiveProducerService {

    private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;

    @Value(value = "${CUSTOMER_TOPIC}")
    private String topic;

    public ReactiveProducerService(ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    public Mono<SenderResult<Void>> send(String message) {
        log.info("send to topic={}, message={},", topic, message);
        return reactiveKafkaProducerTemplate.send(topic, message)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", message, senderResult.recordMetadata().offset()));
    }
}