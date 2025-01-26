package com.anemona.consumer1.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer1Service {

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        System.out.println("Mensaje Recibido: " + message);
    }
    
}
