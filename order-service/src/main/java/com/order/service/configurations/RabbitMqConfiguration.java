package com.order.service.configurations;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    @Value("${order.dlq.queue}")
    String orderDlq;
    @Value("${order.first.queue}")
    String firstQueue;
    @Value("${order.second.queue}")
    String secondQueue;
    @Value("${order.exchange.name}")
    String exchange;

    @Value("${order.exchange.dlq}")
    String dlqExchange;
    @Value("${rabbit.mq.host}")
    String host;
    @Value("${rabbit.mq.port}")
    int port;
    @Value("${rabbit.mq.user}")
    String userName;
    @Value("${rabbit.mq.password}")
    String passWord;

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(orderDlq).build();
    }

    @Bean
    Queue secondQueue() {
        return QueueBuilder.durable(secondQueue).withArgument("x-dead-letter-exchange", dlqExchange).withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }

    @Bean
    Queue firstQueue() {
        return QueueBuilder.durable(firstQueue).withArgument("x-dead-letter-exchange", dlqExchange).withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
    }

    @Bean
    Binding bindingSecond() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with(secondQueue);
    }
    @Bean
    Binding bindingFirst() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(firstQueue);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(passWord);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    ApplicationRunner runner(ConnectionFactory cf) {
        return args -> {
            boolean open = false;
            while (!open) {
                try {
                    cf.createConnection().close();
                    open = true;
                } catch (Exception e) {
                    Thread.sleep(5000);
                }
            }
        };
    }
}
