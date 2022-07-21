package com.demo.grpc.server;

import com.demo.grpc.server.service.PersonService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcServerApp {

    private static final Logger log = LoggerFactory.getLogger(GrpcServerApp.class);

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder
                .forPort(8082)
                .addService(new PersonService())
                .build();

        server.start();
        log.info("[GrpcServerApp] Server started..");
        server.awaitTermination();
    }

}
