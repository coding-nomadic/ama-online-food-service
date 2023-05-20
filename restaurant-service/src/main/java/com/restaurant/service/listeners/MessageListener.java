package com.restaurant.service.listeners;

import com.restaurant.service.models.OrderDtoResponse;
import com.restaurant.service.services.RestaurantService;
import com.restaurant.service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    private RestaurantService restaurantService;

    public MessageListener(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @RabbitListener(queues = "${order.second.queue}")
    public void receiveMessage(OrderDtoResponse orderDtoResponse) {
        try {
            log.info("Incoming Message from the order process queue : {}", JsonUtils.toString(orderDtoResponse));
            restaurantService.updateOrderDetails(orderDtoResponse);
        } catch (Exception exception) {
            log.error("Error occurred {}", exception.getLocalizedMessage());
            throw new AmqpRejectAndDontRequeueException("Exception occurred in Message Listener");
        }
    }
}
