package erik.study.spring.rabbitmq;

import erik.study.spring.rabbitmq.config.Config;
import erik.study.spring.rabbitmq.service.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author erik.wang
 * @date 2020-03-06 23:42
 * @description
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        MessageListener messageListener = context.getBean(MessageListener.class);
        messageListener.handMessage();

    }

}
