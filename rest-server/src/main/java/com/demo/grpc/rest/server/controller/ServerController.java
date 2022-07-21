package com.demo.grpc.rest.server.controller;

import com.demo.grpc.rest.server.dto.Person;
import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    private final Faker faker;

    public ServerController() {
        this.faker = new Faker();
    }

    @PostMapping("/to_string")
    public String getToString(@RequestBody Person person) {
        person.setProfession(faker.company().profession());
        return person.toString();
    }

}
