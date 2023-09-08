@Configuration
public class RabbitMqConfiguration {

    private final String host;
    private final int port;
    private final String userName;
    private final String passWord;

    public RabbitMqConfiguration(
            @Value("${rabbit.mq.host}") String host,
            @Value("${rabbit.mq.port}") int port,
            @Value("${rabbit.mq.user}") String userName,
            @Value("${rabbit.mq.password}") String passWord
    ) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.passWord = passWord;
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
}
