package com.anemona.consumer1.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Consumer1Config {
    public static final String QUEUE_ALERTAS = "alertas.queue";

    @Bean
    public Queue alertQueue() {
        return new Queue(QUEUE_ALERTAS, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    
}
