package com.email.service.listeners;

import com.email.service.mailsender.EmailSender;
import com.email.service.models.OrderDtoResponse;
import com.email.service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    private EmailSender emailSender;

    public MessageListener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "${order.second.queue}")
    public void receiveMessage(OrderDtoResponse orderDtoResponse) {
        try {
            log.info("Incoming Message from the order confirmation queue : {}", JsonUtils.toString(orderDtoResponse));
            emailSender.sendMail(orderDtoResponse);
        } catch (Exception exception) {
            log.error("Error occurred {}", exception.getLocalizedMessage());
            throw new AmqpRejectAndDontRequeueException("Exception occurred in Message Listener");
        }
    }
}
