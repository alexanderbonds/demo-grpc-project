package com.demo.grpc.rest.client.service;

import com.demo.grpc.rest.client.dto.Person;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class ClientService {
    
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    private static final String URL = "http://localhost:8080/to_string";
    
    private final RestTemplate restTemplate;
    private final Faker faker;

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.faker = new Faker();
    }
    
    @PostConstruct
    void sendRequests() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            Person person = new Person(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.random().nextInt(20, 80)
            );
            String toString = restTemplate.postForObject(URL, person, String.class);
            log.info("[ClientService] Person {}: {}", i, toString);
        }
        long endTime = System.currentTimeMillis();
        log.info("[ClientService] execution time {}ms", endTime - startTime);
    }
    
}
