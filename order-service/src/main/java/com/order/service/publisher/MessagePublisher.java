package com.order.service.publisher;

import com.order.service.exceptions.OrderServiceException;
import com.order.service.models.OrderDtoResponse;
import com.order.service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessagePublisher {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${order.exchange.name}")
    String exchange;
    public void publishMessage(OrderDtoResponse orderDtoResponse) {
        amqpTemplate.convertAndSend(exchange, "", orderDtoResponse);
        try {
            log.info("Message sent successfully to Exchange with {}", JsonUtils.toString(orderDtoResponse));
        } catch (IOException exception) {
            log.error("Error occurred while converting to JSON String {}", exception.getLocalizedMessage());
            throw new OrderServiceException(exception.getLocalizedMessage(), "102");
        }
    }
}
