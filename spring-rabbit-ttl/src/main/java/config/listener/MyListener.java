package config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author erik.wang
 * @date 2020-06-10 18:07
 */
@Component
public class MyListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MyListener.class);
    private final static String CHARSET_UTF8 = "UTF-8";

    public void onMessage(Message message) {
        try {
            String messageContent = new String(message.getBody(), CHARSET_UTF8);
            logger.info("message content:{}", messageContent);
            throw new RuntimeException("by erik.");
        } catch (Throwable throwable) {
            logger.error("{} error", this.getClass().getSimpleName(), throwable);
            throw new AmqpRejectAndDontRequeueException(throwable);
        }

    }
}
