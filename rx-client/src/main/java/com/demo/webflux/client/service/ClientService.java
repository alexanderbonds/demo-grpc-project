package com.demo.webflux.client.service;

import com.demo.webflux.client.dto.Person;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final WebClient webClient;
    private final Faker faker;

    public ClientService(WebClient webClient) {
        this.webClient = webClient;
        this.faker = new Faker();
    }

    @PostConstruct
    public void makeRequests() {
        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < 10_000; i++) {
            Person person = new Person(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.random().nextInt(20, 80)
            );

            String toString = webClient.post()
                    .uri("/to_string")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(person), Person.class)
                    .retrieve()
                    .bodyToMono(String.class).block();

            log.info("[ClientService] Person {}: {}", i, toString);
        }

        long endTime = System.currentTimeMillis();
        log.info("[ClientService] delta = {}", endTime - beginTime);
    }
}
