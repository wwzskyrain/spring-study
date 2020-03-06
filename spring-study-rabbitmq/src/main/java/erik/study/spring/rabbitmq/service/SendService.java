package erik.study.spring.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import erik.study.spring.rabbitmq.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author erik.wang
 * @date 2020-03-06 19:11
 * @description
 */
@Component
public class SendService {

    private static final Logger logger = LoggerFactory.getLogger(SendService.class);

    @Resource(name = "messageWithTtlTemplate")
    private RabbitTemplate rabbitTemplate;


    public void sendOrderDesc() {
        for (int i = 0; i < 5; i++) {

            long expireTime = (5 - i) * 10L;
            Order order = new Order();
            order.setBuyerId(123123L);
            order.setId(expireTime);
            order.setOrderNo("2020-03-05-00001");
            order.setProductName("5天儿童会员");

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setExpiration(String.valueOf(expireTime*1000));

            Message message = new Message(JSON.toJSONString(order).getBytes(), messageProperties);
            rabbitTemplate.send("1", message);
            logger.info("success_to_send_message, {}", JSON.toJSONString(order));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendOrderAsc() {

        for (int i = 0; i < 5; i++) {

            long expireTime = i * 10L;
            Order order = new Order();
            order.setBuyerId(1111L);
            order.setId(expireTime);
            order.setOrderNo("2020-03-06-00001");
            order.setProductName("5天儿童会员1");

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setExpiration(String.valueOf(expireTime*1000));

            Message message = new Message(JSON.toJSONString(order).getBytes(), messageProperties);
            rabbitTemplate.send("1", message);
            logger.info("success_to_send_message, {}", JSON.toJSONString(order));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void buildDelayOrderMessage() {


    }


}
