package com.order.service.publisher;

import com.order.service.constants.OrderConstants;
import com.order.service.entities.Order;
import com.order.service.exceptions.OrderServiceException;
import com.order.service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessagePublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${order.topic.name}")
    String orderConfirmationTopic;


    public MessagePublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /** publish message **/
    public void publishMessage(Order order) {
        try {
            //amqpTemplate.convertAndSend(exchange, "order-confirmation", orderDtoResponse);
            //amqpTemplate.convertAndSend(exchange, "order-process", orderDtoResponse);
            kafkaTemplate.send(orderConfirmationTopic, JsonUtils.toString(order));
            kafkaTemplate.send(orderConfirmationTopic, JsonUtils.toString(order));
            log.info("Message sent successfully to Order Process and Order Confirmation Queues {}", JsonUtils.toString(order));
        } catch (Exception exception) {
            log.error("Error occurred while converting to JSON String {}", exception.getLocalizedMessage());
            throw new OrderServiceException(exception.getLocalizedMessage(), OrderConstants.MENU_NOT_FOUND_ERROR_CODE);
        }
    }
}
