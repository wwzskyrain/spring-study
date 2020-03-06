package erik.study.spring.rabbitmq.config;

import erik.study.spring.rabbitmq.service.SendService;
import org.junit.Test;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author erik.wang
 * @date 2020-03-06 19:08
 * @description
 */

@Configuration
@ComponentScan(basePackages = {"erik.study.spring.rabbitmq.service"})
public class Config {

    private final static String EXCHANGE_EXPIRE_MESSAGE = "exchange.to.test.expire.message";
    private final static String QUEUE_EXPIRE_MESSAGE = "dead.letter.queue.to.test.message.expire";

    @Bean
    public CachingConnectionFactory getCachingConnectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setChannelCacheSize(50);

        return connectionFactory;
    }

    @Bean("messageWithTtlTemplate")
    public RabbitTemplate getRabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE_EXPIRE_MESSAGE);
        return rabbitTemplate;
    }


    @Bean("receiveExpiredMessageTemplate")
    public RabbitTemplate getReceiveExpiredMessageTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setDefaultReceiveQueue(QUEUE_EXPIRE_MESSAGE);
        return rabbitTemplate;
    }





}
