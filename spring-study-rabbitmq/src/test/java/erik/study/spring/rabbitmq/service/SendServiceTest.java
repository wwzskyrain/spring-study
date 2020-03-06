package erik.study.spring.rabbitmq.service;

import erik.study.spring.rabbitmq.config.Config;
import erik.study.spring.rabbitmq.service.SendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author erik.wang
 * @date 2020-03-06 19:30
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class SendServiceTest {

    @Autowired
    private SendService sendService;

    @Test
    public void test_send_order_asc(){
        sendService.sendOrderAsc();
    }

    @Test
    public void test_send_order_desc(){
        sendService.sendOrderDesc();
    }

}
