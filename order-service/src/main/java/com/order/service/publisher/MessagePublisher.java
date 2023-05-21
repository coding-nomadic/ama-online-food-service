package com.order.service.publisher;

import com.order.service.exceptions.OrderServiceException;
import com.order.service.models.OrderDtoResponse;
import com.order.service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessagePublisher {

    private AmqpTemplate amqpTemplate;
    @Value("${order.exchange.name}")
    String exchange;

    public MessagePublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /** publish message **/
    public void publishMessage(OrderDtoResponse orderDtoResponse) {
        try {
            amqpTemplate.convertAndSend(exchange, "order-confirmation", orderDtoResponse);
            amqpTemplate.convertAndSend(exchange, "order-process", orderDtoResponse);
            log.info("Message sent successfully to Order Process and Order Confirmation Queues {}", JsonUtils.toString(orderDtoResponse));
        } catch (Exception exception) {
            log.error("Error occurred while converting to JSON String {}", exception.getLocalizedMessage());
            throw new OrderServiceException(exception.getLocalizedMessage(), "102");
        }
    }
}
