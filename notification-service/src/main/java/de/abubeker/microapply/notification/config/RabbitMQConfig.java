package de.abubeker.microapply.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String APPLICATION_CREATED_QUEUE = "applicationCreatedQueue";
    public static final String APPLICATION_CREATED_EXCHANGE = "applicationCreatedExchange";
    public static final String ROUTING_KEY = "application.created";

    @Bean
    public DirectExchange applicationCreatedExchange() {
        return new DirectExchange(APPLICATION_CREATED_EXCHANGE);
    }

    @Bean
    public Queue applicationCreatedQueue() {
        return new Queue(APPLICATION_CREATED_QUEUE);
    }

    @Bean
    public Binding binding(Queue applicationCreatedQueue, DirectExchange applicationCreatedExchange) {
        return BindingBuilder.bind(applicationCreatedQueue).to(applicationCreatedExchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
