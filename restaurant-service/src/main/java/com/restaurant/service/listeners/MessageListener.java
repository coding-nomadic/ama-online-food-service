package com.restaurant.service.listeners;

import com.restaurant.service.constants.RestaurantConstants;
import com.restaurant.service.models.OrderDtoResponse;
import com.restaurant.service.services.RestaurantService;
import com.restaurant.service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    private RestaurantService restaurantService;

    public MessageListener(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @KafkaListener(topics = RestaurantConstants.TOPIC_NAME, groupId = RestaurantConstants.CONSUMER_GROUP)
    public void receiveMessage(String message) {
        try {
            final OrderDtoResponse orderDtoResponse = JsonUtils.convertWithClass(message, OrderDtoResponse.class);
            log.info("Incoming Message from the order process queue : {}", JsonUtils.toString(orderDtoResponse));
            restaurantService.updateOrderDetails(orderDtoResponse);
        } catch (Exception exception) {
            log.error("Error occurred {}", exception.getLocalizedMessage());
            throw new RuntimeException("Exception occurred in Message Listener");
        }
    }
}
