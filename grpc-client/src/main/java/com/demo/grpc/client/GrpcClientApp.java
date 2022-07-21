package com.demo.grpc.client;

import com.demo.grpc.generated.PersonRequest;
import com.demo.grpc.generated.PersonResponse;
import com.demo.grpc.generated.PersonServiceGrpc;
import com.github.javafaker.Faker;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClientApp {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        PersonServiceGrpc.PersonServiceBlockingStub stub
                = PersonServiceGrpc.newBlockingStub(channel);

        Faker faker = new Faker();

        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            PersonRequest request = PersonRequest.newBuilder()
                    .setFirstName(faker.name().firstName())
                    .setLastName(faker.name().lastName())
                    .setAge(faker.random().nextInt(20, 80))
                    .build();

            PersonResponse response = stub.getToString(request);
            System.out.println(response);
        }

        channel.shutdown();

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
    }

}
