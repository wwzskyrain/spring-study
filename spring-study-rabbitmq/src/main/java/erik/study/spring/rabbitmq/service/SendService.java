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
import java.util.ArrayList;
import java.util.List;
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
        List<Order> orders = buildDelayOrderMessage();
        for (int i = orders.size() - 1; i >= 0; i--) {
            sendMessage(orders.get(i));
        }
    }

    public void sendOrderAsc() {
        List<Order> orders = buildDelayOrderMessage();
        for (int i = 0; i < orders.size(); i++) {
            sendMessage(orders.get(i));
        }

    }

    private void sendMessage(Order order) {
        MessageProperties properties = new MessageProperties();
        properties.setExpiration(String.valueOf(order.getId() * 10 * 1000));

        Message message = new Message(JSON.toJSONBytes(order), properties);
        rabbitTemplate.send("1", message);
        logger.info("success_to_send_message:{}", JSON.toJSONString(order));
        waiteSomeSecond(1);
    }

    private List<Order> buildDelayOrderMessage() {

        List<Order> orders = new ArrayList<Order>();
        for (int i = 0; i < 5; i++) {
            Order order = new Order();
            order.setBuyerId(i * 1111L);
            order.setId(i * 1L);
            order.setOrderNo("2020-03-06-000" + i);
            order.setProductName("5天儿童会员" + i);
            orders.add(order);
        }
        return orders;
    }

    private void waiteSomeSecond(Integer sleepSeconds) {
        try {
            TimeUnit.SECONDS.sleep(sleepSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
