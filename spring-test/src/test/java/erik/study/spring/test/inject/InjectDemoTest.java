package erik.study.spring.test.inject;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.ClientParameters;
import com.rabbitmq.http.client.GetAckMode;
import com.rabbitmq.http.client.GetEncoding;
import com.rabbitmq.http.client.domain.InboundMessage;
import com.rabbitmq.http.client.domain.QueueInfo;
import erik.study.spring.test.inject.component.InjectDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @Date 2019-09-14
 * @Created by erik
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:my-beans.xml"})
public class InjectDemoTest {

    private final static Logger logger = LoggerFactory.getLogger(InjectDemoTest.class);

    @Autowired
    private InjectDemo injectDemo;

    @Autowired
    private SimpleDriverDataSource dataSource;

    @Test
    public void test_inject() {

        logger.info("Url: {}", dataSource.getUrl());
        logger.info("User name: {}", dataSource.getUsername());
        logger.info("Password: {}", dataSource.getPassword());


        logger.info(injectDemo.getDbUrl());
        logger.info(injectDemo.getDbUserName());
        logger.info(injectDemo.getDbPassword());

    }


    @Test
    public void test_get_with_rabbit_http_client() {

        String queueName = "xmkp.edu.album.success.fulfill.message.ttl.dead.letter.queue";

        Client rabbitMqClient = null;
        try {
            rabbitMqClient = new Client(
                    new ClientParameters().url("http://192.168.3.161:15672/api/").username("guest").password("guest")
            );


            List<InboundMessage> inboundMessages = rabbitMqClient.get("/", queueName, 3, GetAckMode.NACK_REQUEUE_TRUE, GetEncoding.AUTO, 3000);

            System.out.println("inboundMessage={}" + inboundMessages);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test_get_queue() {
        try {
            Client client = new Client(
                    new ClientParameters().url("http://192.168.3.161:15672/api/").username("guest").password("guest")
            );
            String queueName = "xmkp.edu.album.success.fulfill.message.ttl.dead.letter.queue";
            logger.info("encode:{}", UriUtils.encodePathSegment("/", "UTF-8"));
            logger.info("encode:{}", UriUtils.encodePathSegment(UriUtils.encodePathSegment("/", "UTF-8"), "UTF-8"));

            QueueInfo queueInfo = client.getQueue(UriUtils.encodePathSegment("/", "UTF-8"), queueName);
            logger.info("queueInfo={}", queueInfo);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


}
