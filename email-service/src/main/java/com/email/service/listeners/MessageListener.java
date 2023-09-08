@Component
@Slf4j
public class MessageListener {

    private final EmailSender emailSender;

    public MessageListener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "${order.second.queue}")
    public void receiveMessage(OrderDtoResponse orderDtoResponse) {
        try {
            logIncomingMessage(orderDtoResponse);
            sendEmail(orderDtoResponse);
        } catch (Exception exception) {
            handleException(exception);
        }
    }

    private void logIncomingMessage(OrderDtoResponse orderDtoResponse) {
        log.info("Incoming Message from the order confirmation queue: {}", JsonUtils.toString(orderDtoResponse));
    }

    private void sendEmail(OrderDtoResponse orderDtoResponse) {
        emailSender.sendMail(orderDtoResponse);
    }

    private void handleException(Exception exception) {
        log.error("Error occurred: {}", exception.getMessage(), exception);
        throw new AmqpRejectAndDontRequeueException("Exception occurred in Message Listener", exception);
    }
}
