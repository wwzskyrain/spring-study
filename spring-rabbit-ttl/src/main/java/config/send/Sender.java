package config.send;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import config.RabbitConfig;
import config.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author erik.wang
 * @date 2020-06-12 09:55
 */

public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitConfig.host);
        connectionFactory.setPort(RabbitConfig.port);
        connectionFactory.setUsername(RabbitConfig.userName);
        connectionFactory.setPassword(RabbitConfig.password);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchange = "erik.test.ttl.retry.exchange";

        List<Student> students = createStudent();
        for (Student student : students) {
            channel.basicPublish(exchange, "", null, JSON.toJSONString(student).getBytes());
        }

        logger.info("over");
        TimeUnit.SECONDS.sleep(5);

        System.exit(0);

    }

    public static List<Student> createStudent() {

        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student(i, "name" + i, i + 10, true);
            students.add(student);
        }
        return students;
    }

}
