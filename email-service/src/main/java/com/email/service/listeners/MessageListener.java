package com.email.service.listeners;

import com.email.service.mailsender.EmailSender;
import com.email.service.models.OrderDtoResponse;
import com.email.service.utils.JsonUtils;
import constants.EmailConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageListener {

    private final EmailSender emailSender;

    public MessageListener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @KafkaListener(topics = EmailConstants.TOPIC_NAME, groupId = EmailConstants.CONSUMER_GROUP)
    public void receiveMessage(String message) {
        try {
            final OrderDtoResponse orderDtoResponse = logIncomingMessage(message);
            sendEmail(orderDtoResponse);
        } catch (Exception exception) {
            handleException(exception);
        }
    }

    private OrderDtoResponse logIncomingMessage(String message) throws IOException {
        log.info("Incoming Message from the order confirmation queue: {}", message);
        return JsonUtils.convertWithClass(message, com.email.service.models.OrderDtoResponse.class);
    }

    private void sendEmail(OrderDtoResponse orderDtoResponse) {
        emailSender.sendMail(orderDtoResponse);
    }

    private void handleException(Exception exception) {
        log.error("Error occurred: {}", exception.getMessage(), exception);
        throw new RuntimeException("Exception occurred in Message Listener", exception);
    }
}
