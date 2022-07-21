package com.demo.grpc.server.service;

import com.demo.grpc.generated.PersonRequest;
import com.demo.grpc.generated.PersonResponse;
import com.demo.grpc.generated.PersonServiceGrpc;
import com.github.javafaker.Faker;
import io.grpc.stub.StreamObserver;

public class PersonService extends PersonServiceGrpc.PersonServiceImplBase {

    private static final Faker faker = new Faker();

    @Override
    public void getToString(PersonRequest request, StreamObserver<PersonResponse> responseObserver) {
        String toString = new StringBuilder("[")
                .append("firstName=").append(request.getFirstName()).append(", ")
                .append("lastName=").append(request.getLastName()).append(", ")
                .append("age=").append(request.getAge()).append(", ")
                .append("profession=").append(faker.company().profession())
                .append("]").toString();

        PersonResponse response = PersonResponse.newBuilder()
                .setToString(toString)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
