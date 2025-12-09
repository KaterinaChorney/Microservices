package com.flowerlabvlada;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderProcessor {

    @Incoming("orders-in")
    public void processOrder(String jsonEvent) {
        System.out.println(">>> Message received from RabbitMQ: " + jsonEvent);
    }
}