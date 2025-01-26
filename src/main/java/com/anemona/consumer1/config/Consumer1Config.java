package com.anemona.consumer1.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Consumer1Config {

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", true);
    }
    
}
