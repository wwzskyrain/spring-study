package erik.study.spring.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import erik.study.spring.rabbitmq.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author erik.wang
 * @date 2020-03-06 22:39
 * @description
 */
@Component
public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Resource(name = "receiveExpiredMessageTemplate")
    private RabbitTemplate rabbitTemplate;

    public void handMessage() {
        while (true) {
            Message message = rabbitTemplate.receive(-1);
            logger.info("received_massage = {}", new String(message.getBody()));
        }

    }

}
