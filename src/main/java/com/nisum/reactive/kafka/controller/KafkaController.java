package com.nisum.reactive.kafka.controller;

import com.nisum.reactive.kafka.service.ReactiveProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class KafkaController {

    private final ReactiveProducerService reactiveProducerService;

    public KafkaController(ReactiveProducerService reactiveProducerService) {
        this.reactiveProducerService = reactiveProducerService;
    }

    @PostMapping(value = "/publish")
    public Mono<ResponseEntity<String>> publish(@RequestBody(required = false) String message) {
        return Mono.just(message)
                .zipWhen(response -> this.reactiveProducerService.send(message))
                .map(response -> ResponseEntity.status(HttpStatus.OK).body("Message sent successfully"))
                .onErrorResume(throwable -> {
                    log.error("Error while publish message:: cause: ", throwable);
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while sending message"));
                });
    }
}
